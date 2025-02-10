package com.yashkolte.quizquestionservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import java.util.Base64;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    public static HttpHeaders createHeaders(String username, String password) {
        HttpHeaders headers = new HttpHeaders();
        String auth = username + ":" + password;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        String authHeader = "Basic " + encodedAuth;
        headers.set(HttpHeaders.AUTHORIZATION, authHeader);
        return headers;
    }
}