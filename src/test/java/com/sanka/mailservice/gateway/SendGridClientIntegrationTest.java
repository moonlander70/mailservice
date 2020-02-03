package com.sanka.mailservice.gateway;

import com.sanka.mailservice.model.Email;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@SpringBootTest
class SendGridClientIntegrationTest {

    @Autowired
    private SendGridClient sendGridClient;

    // NOTE: with this test, I was manually verifying that I was receiving the email
    // In actual production code, we would be using Hoverfly or an equivalent capture / simulator
    // to simulate the HTTP calls
    @Test
    @DisplayName("GIVEN all valid inputs THEN ensure the email sends")
    void sendEmail() {
        val email = Email.builder()
                .from("test@hotmail.com")
                .to(Set.of("moonlander70@gmail.com"))
                .message("test message from sendgrid")
                .subject("test sendgrid")
                .build();

        sendGridClient.sendEmail(email);
    }
}