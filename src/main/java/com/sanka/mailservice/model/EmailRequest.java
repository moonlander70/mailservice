package com.sanka.mailservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@JsonDeserialize(builder = EmailRequest.EmailRequestBuilder.class)
@Builder
@Value // Needed for Json Deserialization. Would otherwise just use Getter
public class EmailRequest {

    @Email
    @NotBlank(message = "'from' cannot be blank or null")
    private String from;

    @NotEmpty(message = "'to' field cannot be blank or null")
    private Set<@Email String> to;

    private Set<@Email String> cc;

    private Set<@Email String> bcc;

    @NotBlank(message = "'subject' cannot be blank or null")
    private String subject;

    @NotBlank(message = "'message' cannot be blank or null")
    private String message;

    @JsonPOJOBuilder(withPrefix = "")
    public static class EmailRequestBuilder {

    }


}
