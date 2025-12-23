package com.dealdigger.crawler;

import com.dealdigger.dto.CrawlRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
public class CrawlingUtil {

    private final WebClient webClient;

    public String execute(CrawlRequest req) {
        try {
            WebClient.RequestBodySpec spec = webClient
                    .method(req.getMethod())
                    .uri(uriBuilder -> {
                        uriBuilder.path(req.getUrl());
                        if (req.getQueryParams() != null) {
                            req.getQueryParams().forEach(uriBuilder::queryParam);
                        }
                        return uriBuilder.build();
                    });

            if (req.getHeaders() != null) {
                req.getHeaders().forEach(spec::header);
            }

            WebClient.RequestHeadersSpec<?> headersSpec = spec;

            if (req.getMethod() == HttpMethod.POST) {
                if (req.getFormData() != null) {
                    headersSpec = spec
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .bodyValue(req.getFormData());
                } else if (req.getJsonBody() != null) {
                    headersSpec = spec
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(req.getJsonBody());
                }
            }

            return headersSpec
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofSeconds(5))
                    .block();
        } catch (Exception e) {
            log.error("Crawling failed: {} {}", req.getMethod(), req.getUrl(), e);
            return null;
        }
    }
}