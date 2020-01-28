package com.sanka.mailservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;

@Builder
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmailAddress {

    @JsonProperty("email")
    private String email;

    @JsonProperty("name")
    private String name;

}
