package com.sanka.mailservice.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class ErrorMessage {
    private String name;
    private List<ErrorDetail> details;


}
