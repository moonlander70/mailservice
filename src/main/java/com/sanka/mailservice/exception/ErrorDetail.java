package com.sanka.mailservice.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ErrorDetail {
    private String location;
    private String param;
    private String msg;
    private String message;
    private String value;
}
