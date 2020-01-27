package com.sanka.mailservice.model.sendgrid;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;

@Builder
@EqualsAndHashCode
public class EmailAddress {

    @JsonProperty("email")
    private String email;

    @JsonProperty("name")
    private String name;

}
