package com.sanka.mailservice.service;

import com.sanka.mailservice.exception.NoRecipientsFoundException;
import com.sanka.mailservice.gateway.EmailGateway;
import com.sanka.mailservice.model.Email;
import com.sanka.mailservice.model.EmailRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private EmailGateway emailGateway;

    @Captor
    ArgumentCaptor<Email> emailCaptor;

    // object under test
    private EmailService emailService;

    @BeforeEach
    void setup() {
        emailService = new EmailService(emailGateway);
    }

    @Test
    @DisplayName("GIVEN no email recipients WHEN sendEmail EXPECT NoRecipientsFoundException")
    void sendEmail_noRecipients() {
        // Given
        final EmailRequest emailRequest = EmailRequest.builder()
                .from("test@test.com")
                .build();

        // Then
        assertThrows(NoRecipientsFoundException.class, () -> emailService.sendEmail(emailRequest));

    }

    @Test
    @DisplayName("GIVEN 'to' has email recipients WHEN sendEmail THEN send to email gateway")
    void sendEmail_hasTo() {
        // Given
        final EmailRequest emailRequest = EmailRequest.builder()
                .from("test@test.com")
                .to(Set.of("test@test.com"))
                .build();

        // When
        emailService.sendEmail(emailRequest);

        // Then
        verify(emailGateway, times(1)).sendEmail(emailCaptor.capture());

        final Email actualEmail = emailCaptor.getValue();

        assertThat(actualEmail.getFrom()).isEqualTo(emailRequest.getFrom());
        assertThat(actualEmail.getTo()).isEqualTo(emailRequest.getTo());
        assertThat(actualEmail.getSubject()).isEqualTo(emailRequest.getSubject());
        assertThat(actualEmail.getMessage()).isEqualTo(emailRequest.getMessage());
        assertThat(actualEmail.getLocalDateTime()).isEqualToIgnoringSeconds(LocalDateTime.now(ZoneOffset.UTC));
    }

    @Test
    @DisplayName("GIVEN 'cc' has email recipients WHEN sendEmail THEN send to email gateway")
    void sendEmail_hasCc() {
        // Given
        final EmailRequest emailRequest = EmailRequest.builder()
                .from("test@test.com")
                .cc(Set.of("test@test.com"))
                .build();

        // When
        emailService.sendEmail(emailRequest);

        // Then
        verify(emailGateway, times(1)).sendEmail(emailCaptor.capture());

        final Email actualEmail = emailCaptor.getValue();

        assertThat(actualEmail.getFrom()).isEqualTo(emailRequest.getFrom());
        assertThat(actualEmail.getCc()).isEqualTo(emailRequest.getCc());
        assertThat(actualEmail.getSubject()).isEqualTo(emailRequest.getSubject());
        assertThat(actualEmail.getMessage()).isEqualTo(emailRequest.getMessage());
        assertThat(actualEmail.getLocalDateTime()).isEqualToIgnoringSeconds(LocalDateTime.now(ZoneOffset.UTC));

    }

    @Test
    @DisplayName("GIVEN 'bcc' has email recipients WHEN sendEmail THEN send to email gateway")
    void sendEmail_hasBcc() {
        // Given
        final EmailRequest emailRequest = EmailRequest.builder()
                .from("test@test.com")
                .bcc(Set.of("test@test.com"))
                .build();

        // When
        emailService.sendEmail(emailRequest);

        // Then
        verify(emailGateway, times(1)).sendEmail(emailCaptor.capture());

        final Email actualEmail = emailCaptor.getValue();

        assertThat(actualEmail.getFrom()).isEqualTo(emailRequest.getFrom());
        assertThat(actualEmail.getBcc()).isEqualTo(emailRequest.getBcc());
        assertThat(actualEmail.getSubject()).isEqualTo(emailRequest.getSubject());
        assertThat(actualEmail.getMessage()).isEqualTo(emailRequest.getMessage());
        assertThat(actualEmail.getLocalDateTime()).isEqualToIgnoringSeconds(LocalDateTime.now(ZoneOffset.UTC));

    }

}