package com.dealdigger.crawler;

import com.dealdigger.dto.CrawlRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;

import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class KakaoCrawler {

    private static final String KAKAO_SEARCH_PATH = "/a/f-s/search/products";
    private static final String KAKAO_REFERER_BASE = "https://store.kakao.com/search/result/product?q=";
    
    private final CrawlingUtil crawlingUtil;

    public String crawl(String keyword, int page, int size) {

        String now = String.valueOf(System.currentTimeMillis());

        CrawlRequest req = CrawlRequest.builder()
                .method(HttpMethod.GET)
                .url(KAKAO_SEARCH_PATH)
                .queryParams(Map.of(  
                        "q", keyword,
                        "searchType", "recent",
                        "sort", "POPULAR",
                        "timestamp", now,
                        "page", String.valueOf(page),
                        "size", String.valueOf(size),
                        "_", now
                ))
                .headers(Map.of(
                        HttpHeaders.ACCEPT, "application/json, text/plain, */*",
                        HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE,
                        HttpHeaders.REFERER, KAKAO_REFERER_BASE + keyword + "&searchType=recent"
                ))
                .build();

        return crawlingUtil.execute(req);
    }
}