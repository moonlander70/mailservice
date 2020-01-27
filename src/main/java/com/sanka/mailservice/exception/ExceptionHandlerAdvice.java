package com.sanka.mailservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage handleException(final MethodArgumentNotValidException exception) {
        final List<ErrorDetail> errorDetails = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> ErrorDetail.builder()
                        .msg(fieldError.getDefaultMessage())
                        .location("params")
                        .param(fieldError.getField())
                        .value(String.valueOf(fieldError.getRejectedValue()))
                        .build()
                )
                .collect(Collectors.toList());


        return ErrorMessage.builder()
                .name("ValidationError")
                .details(errorDetails)
                .build();
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage handleException(final ConstraintViolationException exception) {
        final List<ErrorDetail> errorDetails = exception.getConstraintViolations()
                .stream()
                .map(error -> ErrorDetail.builder()
                        .msg(error.getMessage())
                        .param(String.valueOf(error.getPropertyPath()))
                        .location("params")
                        .value(String.valueOf(error.getInvalidValue()))
                        .build()
                )
                .collect(Collectors.toList());

        return ErrorMessage.builder()
                .name("ValidationError")
                .details(errorDetails)
                .build();
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage handleException(final NoRecipientsFoundException exception) {
        final List<ErrorDetail> errorDetails = List.of(ErrorDetail.builder()
                .location("params")
                .message(exception.getMessage())
                .build());

        return ErrorMessage.builder()
                .name("ValidationError")
                .details(errorDetails)
                .build();
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_GATEWAY)
    public ErrorMessage handleException(final ServiceDownException exception) {
        return ErrorMessage.builder()
                .details(List.of(ErrorDetail.builder()
                        .message(exception.getMessage())
                        .build()))
                .build();
    }

}
