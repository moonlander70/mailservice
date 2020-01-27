package com.sanka.mailservice.model;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Builder
@Getter
public class Email {
    private String from;
    private Set<String> to;
    private Set<String> cc;
    private Set<String> bcc;
    private String subject;
    private String message;

}
