package com.sanka.mailservice.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.sanka.mailservice.utils.MailUtils;
import com.sanka.mailservice.validation.ValidEmail;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@JsonDeserialize(builder = EmailRequest.EmailRequestBuilder.class)
@Builder
@Value // Needed for Json Deserialization. Would otherwise just use Getter
public class EmailRequest {

    @ValidEmail
    private String from;

    @NotEmpty(message = "'to' field cannot be empty or null")
    private Set<@ValidEmail String> to;

    private Set<@ValidEmail String> cc;

    private Set<@ValidEmail String> bcc;

    @NotBlank(message = "'subject' cannot be blank or null")
    private String subject;

    @NotBlank(message = "'message' cannot be blank or null")
    private String message;

    @JsonPOJOBuilder(withPrefix = "")
    public static class EmailRequestBuilder {

    }


}
