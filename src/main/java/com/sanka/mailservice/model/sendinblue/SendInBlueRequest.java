package com.sanka.mailservice.model.sendinblue;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sanka.mailservice.model.EmailAddress;
import lombok.Builder;
import lombok.Value;

import java.util.Set;

@Builder
@Value
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SendInBlueRequest {
    private EmailAddress sender;
    private EmailAddress replyTo;
    private Set<EmailAddress> to;
    private Set<EmailAddress> cc;
    private Set<EmailAddress> bcc;
    private String subject;
    private String textContent;

}
