package com.sanka.mailservice.gateway;

import com.sanka.mailservice.exception.ServiceDownException;
import com.sanka.mailservice.model.Email;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

@Slf4j
@Component
@AllArgsConstructor
public class EmailGateway {

    private EmailClient sendGridClient;
    private EmailClient sendInBlueClient;

    public void sendEmail(final Email email) {
        try {
            log.info("sending email via sendGrid {} ", email);
            sendGridClient.sendEmail(email);

        } catch (final RestClientException ex) {
            try {
                log.info("sendGrid failed. Sending via SendInBlue {} ", email);
                sendInBlueClient.sendEmail(email);

            } catch (final RestClientException exNext) {
                throw new ServiceDownException();

            }

        }
    }
}
