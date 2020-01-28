package com.sanka.mailservice.gateway;

import com.sanka.mailservice.model.Email;
import com.sanka.mailservice.utils.MailUtils;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component("sendInBlueClient")
@AllArgsConstructor
public class SendInBlueClient implements EmailClient {

    private RestTemplate sendInBlueClientTemplate;

    @Override
    public void sendEmail(final Email email) {
        val sendInBlueRequest = MailUtils.toSendInBlueRequest(email);

        sendInBlueClientTemplate.postForLocation("/smtp/email", sendInBlueRequest);

    }
}
