package com.sanka.mailservice.utils;

import com.sanka.mailservice.model.Email;
import com.sanka.mailservice.model.EmailAddress;
import com.sanka.mailservice.model.sendgrid.Content;
import com.sanka.mailservice.model.sendgrid.Personalization;
import com.sanka.mailservice.model.sendgrid.SendGridMailRequest;
import com.sanka.mailservice.model.sendinblue.SendInBlueRequest;
import lombok.val;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MailUtils {

    public static SendGridMailRequest toSendGridMailRequest(final Email email) {

        val fromAddress = EmailAddress.builder()
                .email(email.getFrom())
                .build();

        val personalizations = List.of(Personalization.builder()
                .to(transformToEmailAddressSet(email.getTo()))
                .cc(transformToEmailAddressSet(email.getCc()))
                .bcc(transformToEmailAddressSet(email.getBcc()))
                .subject(email.getSubject())
                .build());


        return SendGridMailRequest.builder()
                .from(fromAddress)
                .replyTo(fromAddress)
                .personalizations(personalizations)
                .content(List.of(
                        Content.builder()
                        .type("text/plain")
                        .value(email.getMessage())
                        .build()))
                .build();
    }

    public static SendInBlueRequest toSendInBlueRequest(final Email email) {
        val senderAddress = EmailAddress.builder()
                .email(email.getFrom())
                .build();

        return SendInBlueRequest.builder()
                .sender(senderAddress)
                .replyTo(senderAddress)
                .to(transformToEmailAddressSet(email.getTo()))
                .cc(transformToEmailAddressSet(email.getCc()))
                .bcc(transformToEmailAddressSet(email.getBcc()))
                .subject(email.getSubject())
                .textContent(email.getMessage())
                .build();
    }

    private static Set<EmailAddress> transformToEmailAddressSet(final Set<String> emails) {
        if (!CollectionUtils.isEmpty(emails)) {
            return emails.stream()
                .map(e -> EmailAddress.builder().email(String.valueOf(e)).build())
                .collect(Collectors.toSet());
        } else {
            return Collections.emptySet();
        }
    }

}
