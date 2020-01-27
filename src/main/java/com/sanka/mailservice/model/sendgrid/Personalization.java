package com.sanka.mailservice.model.sendgrid;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Personalization {
    private Set<EmailAddress> to;
    private Set<EmailAddress> cc;
    private Set<EmailAddress> bcc;
    private String subject;

}
