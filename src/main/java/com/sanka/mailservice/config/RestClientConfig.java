package com.sanka.mailservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClientConfig {

    @Value("${sendgrid.apiKey}")
    private String apiKey;

    @Value("${sendgrid.baseUrl}")
    private String baseUrl;

    @Bean("sendGridClientTemplate")
    public RestTemplate sendGridClientTemplate() {
        return new RestTemplateBuilder().rootUri(baseUrl)
                .defaultHeader("Authorization", String.format("Bearer %s", apiKey))
                .defaultHeader("Content-Type", "application/json")
                .build();
    }


}
