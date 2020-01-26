package com.sanka.mailservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@JsonDeserialize(builder = EmailRequest.EmailRequestBuilder.class)
@Value
@Builder
public class EmailRequest {

    @Email
    @NotBlank(message = "'from' cannot be blank or null")
    private String from;

    private Set<@Email String> to;

    private Set<@Email String> cc;

    private Set<@Email String> bcc;

    private String subject;

    private String message;

    @JsonPOJOBuilder(withPrefix = "")
    public static class EmailRequestBuilder {

    }


}
