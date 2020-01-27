package com.sanka.mailservice.gateway;

import com.sanka.mailservice.model.Email;
import lombok.val;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@SpringBootTest
class SendGridClientIntegrationTest {

    @Autowired
    private SendGridClient sendGridClient;

    @Disabled
    void sendEmail() {
        val email = Email.builder()
                .from("sheng_ding@baidu.com")
                .to(Set.of("davidvara28@gmail.com"))
                .cc(Set.of("michaelvanbrummen@gmail.com"))
                .bcc(Set.of("moonlander70@gmail.com"))
                .subject("Hi David Vara")
                .message("Hi David. I am much more gooder developer than you. I write it businessu ruru")
                .build();

        sendGridClient.sendEmail(email);
    }
}