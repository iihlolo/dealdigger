package com.dealdigger.scheduler;

import com.dealdigger.service.CrawlerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DailyCrawlerScheduler {

    private final CrawlerService crawlerService;

    /**
     * Daily crawl at midnight KST
     */
    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    public void crawlDailyDeals() {
        try {
            crawlerService.crawlAndSaveKakaoDeals();
            log.info("Daily crawler finished successfully");
        } catch (Exception e) {
            log.error("Error during daily crawling", e);
        }
    }
}