package com.sanka.mailservice.gateway;

import com.sanka.mailservice.model.Email;
import com.sanka.mailservice.model.sendgrid.EmailAddress;
import com.sanka.mailservice.model.sendgrid.Personalization;
import com.sanka.mailservice.model.sendgrid.SendGridMailRequest;
import com.sanka.mailservice.utils.MailUtils;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@AllArgsConstructor
public class SendGridClient implements EmailClient {

    private RestTemplate sendGridClientTemplate;

    @Override
    public void sendEmail(final Email email) {
        val sendGridMailRequest = MailUtils.toSendGridMailRequest(email);
        sendGridClientTemplate.postForLocation("/mail/send",  sendGridMailRequest);

    }



}
