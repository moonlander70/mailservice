package com.sanka.mailservice.model.sendgrid;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SendGridMailRequest {

    private List<Personalization> personalizations;

    private EmailAddress from;

    private EmailAddress replyTo;

    private List<Content> content;

}
