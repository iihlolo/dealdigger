package com.dealdigger.crawler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KakaoCrawler {

    private final String KAKAO_CUSTOM_TABS_URL = "https://store.kakao.com/a/f-s/tabs/custom";
    private final String KAKAO_THEME_DEAL_LIST_URL = "https://store.kakao.com/a/f-s/home/theme-deal/list?date=";
    private final int ONE_PLUS_ONE_DEAL_ID = 213;

    private final CrawlingUtil crawlingUtil;
    private final ObjectMapper objectMapper;

    public JsonNode fetch() {
        try {
            long ts = System.currentTimeMillis();
            String fullUrl = KAKAO_CUSTOM_TABS_URL + "?_=" + ts;

            String tabsHtml = crawlingUtil.fetchHtml(fullUrl);
            JsonNode tabsJson = objectMapper.readTree(tabsHtml);

            String targetUrl = null;
            for (JsonNode tab : tabsJson) {
                if (tab.path("id").asInt() == ONE_PLUS_ONE_DEAL_ID) {
                    targetUrl = tab.path("url").asText();
                    break;
                }
            }

            if (targetUrl == null) {
                log.error("Cannot find 1+1 deal tab in Kakao Store");
                return null;
            }

            String[] parts = targetUrl.split("/");
            String lastPath = parts[parts.length - 1];
            String listUrl = KAKAO_THEME_DEAL_LIST_URL + lastPath + "?_=" + ts;
            
            String listHtml = crawlingUtil.fetchHtml(listUrl);

            log.info("Successfully fetched Kakao Store 1+1 deal products");
            return objectMapper.readTree(listHtml).path("products");

        } catch (Exception e) {
            log.error("Failed to fetch Kakao Store 1+1 deal products", e);
            return null;
        }
    }
}