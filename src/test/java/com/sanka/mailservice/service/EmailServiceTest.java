package com.sanka.mailservice.service;

import com.sanka.mailservice.exception.NoRecipientsFoundException;
import com.sanka.mailservice.gateway.EmailGateway;
import com.sanka.mailservice.model.Email;
import com.sanka.mailservice.model.EmailRequest;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private EmailGateway emailGateway;

    @Captor
    ArgumentCaptor<Email> emailCaptor;

    private EmailService emailService;

    @BeforeEach
    void setup() {
        emailService = new EmailService(emailGateway);
    }

    @Test
    @DisplayName("GIVEN emailRequest is null WHEN sendEmail EXPECT an illegalArgumentException")
    void sendEmail_nullEmailRequest() {
        assertThrows(IllegalArgumentException.class, () -> emailService.sendEmail(null));
    }

    @Test
    @DisplayName("GIVEN no email recipients WHEN sendEmail EXPECT NoRecipientsFoundException")
    void sendEmail_noRecipients() {
        // Given
        val emailRequest = EmailRequest.builder()
                .from("test@test.com")
                .build();

        // Then
        assertThrows(NoRecipientsFoundException.class, () -> emailService.sendEmail(emailRequest));

    }

    // As per the SendGrid spec, the 'to' field needs to have recipients
    @Test
    @DisplayName("GIVEN 'to' has no recipients but 'cc' and 'bbc' have recipients " +
            "WHEN sendEmail EXPECT NoRecpientsFoundException")
    void sendEmail_hasTo() {
        // Given
        val emailRequest = EmailRequest.builder()
                .from("test@test.com")
                .cc(Set.of("test@test.com"))
                .bcc(Set.of("other@other.com"))
                .build();

        // Then
        assertThrows(NoRecipientsFoundException.class, () -> emailService.sendEmail(emailRequest));
    }

    @Test
    @DisplayName("GIVEN 'to' has email recipients WHEN sendEmail THEN send to email gateway")
    void sendEmail_hasBcc() {
        // Given
        val emailRequest = EmailRequest.builder()
                .from("test@test.com")
                .to(Set.of("test@test.com"))
                .subject("Test")
                .message("Hi")
                .build();

        // When
        emailService.sendEmail(emailRequest);

        // Then
        verify(emailGateway, times(1)).sendEmail(emailCaptor.capture());

        val actualEmail = emailCaptor.getValue();

        assertThat(actualEmail.getFrom()).isEqualTo(emailRequest.getFrom());
        assertThat(actualEmail.getBcc()).isEqualTo(emailRequest.getBcc());
        assertThat(actualEmail.getSubject()).isEqualTo(emailRequest.getSubject());
        assertThat(actualEmail.getMessage()).isEqualTo(emailRequest.getMessage());
    }

}