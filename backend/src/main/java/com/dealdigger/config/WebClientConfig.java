package com.dealdigger.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${CRAWLER_USER_AGENT}")
    private String userAgent;
    @Value("${CRAWLER_ACCEPT_LANGUAGE}")
    private String acceptLanguage;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .defaultHeader("User-Agent", userAgent)
                .defaultHeader("Accept-Language", acceptLanguage)
                .build();
    }
}