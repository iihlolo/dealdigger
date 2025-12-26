package com.dealdigger.crawler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Component
@Slf4j
@RequiredArgsConstructor
public class KakaoCrawler {

    private final String KAKAO_THEME_DEAL_LIST_URL = "https://store.kakao.com/a/f-s/home/theme-deal/list?page=0&anchorId=&date=";
    private final String KAKAO_THEME_DEAL_REFERER = "https://store.kakao.com/home/themedeal/";
    
    private final CrawlingUtil crawlingUtil;
    private final ObjectMapper objectMapper;

    public JsonNode fetch() {
        try {
            long ts = System.currentTimeMillis();
            String day = getTodayAsQueryDay();

            String listUrl = KAKAO_THEME_DEAL_LIST_URL + day + "&_=" + ts;
            String referer = KAKAO_THEME_DEAL_REFERER + day;

            String listHtml = crawlingUtil.fetchHtml(listUrl, referer);

            log.info("Successfully fetched today's Kakao Store deal products");
            return objectMapper.readTree(listHtml).path("data").path("products");

        } catch (Exception e) {
            log.info("Cannot fetch any new products from Kakao Store", e);
            return null;
        }
    }

    private String getTodayAsQueryDay() {
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
        return dayOfWeek.name().toLowerCase();
    }
}