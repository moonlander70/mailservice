package com.sanka.mailservice.controller;

import com.sanka.mailservice.model.EmailRequest;
import com.sanka.mailservice.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "emails")
@AllArgsConstructor
public class EmailController {

    private EmailService emailService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void sendEmail(@Valid @RequestBody final EmailRequest emailRequest) {

        emailService.sendEmail(emailRequest);

    }


}
