package com.dealdigger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    private String userAgent = System.getenv("CRAWLER_USER_AGENT");
    private String acceptLanguage = System.getenv("CRAWLER_ACCEPT_LANGUAGE");

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .defaultHeader("User-Agent", userAgent)
                .defaultHeader("Accept-Language", acceptLanguage)
                .build();
    }
}