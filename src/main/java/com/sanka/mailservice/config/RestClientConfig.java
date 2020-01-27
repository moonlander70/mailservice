package com.sanka.mailservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClientConfig {

    @Value("${sendgrid.apiKey}")
    private String sendGridApiKey;

    @Value("${sendgrid.baseUrl}")
    private String sendGridBaseUrl;

    @Value("${send-in-blue.apiKey}")
    private String sendInBlueApiKey;

    @Value("${send-in-blue.apiKey}")
    private String sendInBlueBaseUrl;

    @Bean("sendGridClientTemplate")
    public RestTemplate sendGridClientTemplate() {
        return new RestTemplateBuilder().rootUri(sendGridBaseUrl)
                .defaultHeader("Authorization", String.format("Bearer %s", sendGridApiKey))
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    @Bean("sendInBlueClientTemplate")
    public RestTemplate sendInBlueClientTemplate() {
        return new RestTemplateBuilder().rootUri(sendInBlueBaseUrl)
                .defaultHeader("api-key", sendInBlueApiKey)
                .defaultHeader("content-type", "application/json")
                .build();
    }

}
