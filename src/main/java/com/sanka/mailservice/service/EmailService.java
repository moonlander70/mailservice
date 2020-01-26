package com.sanka.mailservice.service;

import com.sanka.mailservice.exception.NoRecipientsFoundException;
import com.sanka.mailservice.gateway.EmailGateway;
import com.sanka.mailservice.model.Email;
import com.sanka.mailservice.model.EmailRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@AllArgsConstructor
public class EmailService {

    private EmailGateway emailGateway;

    public void sendEmail(final EmailRequest emailRequest) {
        validateRecipients(emailRequest);

        final Email email = Email.builder()
                .from(emailRequest.getFrom())
                .to(emailRequest.getTo())
                .cc(emailRequest.getCc())
                .bcc(emailRequest.getBcc())
                .subject(emailRequest.getSubject())
                .message(emailRequest.getMessage())
                .localDateTime(LocalDateTime.now(ZoneOffset.UTC))
                .build();

        emailGateway.sendEmail(email);

    }

    private void validateRecipients(final EmailRequest emailRequest) {
        if (CollectionUtils.isEmpty(emailRequest.getTo())
                && CollectionUtils.isEmpty(emailRequest.getCc())
                && CollectionUtils.isEmpty(emailRequest.getBcc())) {

            throw new NoRecipientsFoundException();
        }

    }

}
