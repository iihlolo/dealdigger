package com.dealdigger.crawler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
public class CrawlingUtil {

    private final WebClient webClient;

    public String fetchHtml(String url) {
        try {
            return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(5))
                .block();
        } catch (Exception e) {
            log.error("Crawling failed for URL: {}", url, e);
            return null;
        }
    }
}