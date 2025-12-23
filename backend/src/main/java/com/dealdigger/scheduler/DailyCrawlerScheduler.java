package com.dealdigger.scheduler;

import com.dealdigger.domain.WishItem;
import com.dealdigger.repository.WishRepository;
import com.dealdigger.service.CrawlerService;
import com.dealdigger.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DailyCrawlerScheduler {

    private final WishRepository wishRepository;
    private final CrawlerService crawlerService;
    private final ProductService productService;

    /**
     * Daily job at midnight KST
     */
    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    public void dailyJob() {

        log.info("Daily job started.");

        try {
            productService.deleteExpired();
        } catch (Exception e) {
            log.error("Failed to delete expired products", e);
        }

        List<WishItem> wishes = wishRepository.findAll();
        if (wishes.isEmpty()) {
            log.info("No wish items to crawl");
            return;
        }

        log.info("Start daily crawling. wishCount={}", wishes.size());

        for (WishItem wish : wishes) {
            try {
                crawlerService.crawlAndSaveKakaoDeals(wish);
            } catch (Exception e) {
                log.error("Daily crawling failed for wishId={}, keyword={}", wish.getId(), wish.getKeyword(), e);
            }
        }

        log.info("Completed daily job.");
    }
}