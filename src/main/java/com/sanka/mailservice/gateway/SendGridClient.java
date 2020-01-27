package com.sanka.mailservice.gateway;

import com.sanka.mailservice.model.Email;
import com.sanka.mailservice.utils.MailUtils;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component("sendGridClient")
@AllArgsConstructor
public class SendGridClient implements EmailClient {

    private RestTemplate sendGridClientTemplate;

    @Override
    public void sendEmail(final Email email) {
        val sendGridMailRequest = MailUtils.toSendGridMailRequest(email);
        sendGridClientTemplate.postForLocation("/mail/send",  sendGridMailRequest);

    }

}
