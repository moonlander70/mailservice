package com.sanka.mailservice.service;

import com.sanka.mailservice.exception.NoRecipientsFoundException;
import com.sanka.mailservice.gateway.EmailGateway;
import com.sanka.mailservice.model.Email;
import com.sanka.mailservice.model.EmailRequest;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

@Service
@AllArgsConstructor
public class EmailService {

    private EmailGateway emailGateway;

    public void sendEmail(final EmailRequest emailRequest) {
        Assert.notNull(emailRequest, "'emailRequest' cannot be null");
        validateRecipients(emailRequest);

        val email = Email.builder()
                .from(emailRequest.getFrom())
                .to(emailRequest.getTo())
                .cc(emailRequest.getCc())
                .bcc(emailRequest.getBcc())
                .subject(emailRequest.getSubject())
                .message(emailRequest.getMessage())
                .build();

        emailGateway.sendEmail(email);

    }

    private void validateRecipients(final EmailRequest emailRequest) {
        if (CollectionUtils.isEmpty(emailRequest.getTo())) {
            throw new NoRecipientsFoundException();
        }

    }

}
