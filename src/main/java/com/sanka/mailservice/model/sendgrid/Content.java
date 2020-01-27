package com.sanka.mailservice.model.sendgrid;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Content {
    private String type;
    private String value;
}
