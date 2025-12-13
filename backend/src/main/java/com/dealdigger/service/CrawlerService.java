package com.dealdigger.service;

import com.dealdigger.crawler.KakaoCrawler;
import com.dealdigger.crawler.KakaoDealParser;
import com.dealdigger.dto.CreateProductRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CrawlerService {

    private final KakaoCrawler kakaoCrawler;
    private final KakaoDealParser kakaoDealParser;
    private final ProductService productService;

    /**
     * Crawl and Save Kakao Store 1+1 Deals
     */
    public void crawlAndSaveKakaoDeals() {
        try {
            var productsJson = kakaoCrawler.fetch();
            if (productsJson == null || !productsJson.isArray() || productsJson.size() == 0) {
                log.warn("No products found from KakaoCrawler");
                return;
            }

            List<CreateProductRequest> productRequests = kakaoDealParser.parse(productsJson);

            if (productRequests.isEmpty()) {
                log.warn("No products parsed from KakaoCrawler output");
                return;
            }

            for (CreateProductRequest req : productRequests) {
                productService.save(req);
            }

            log.info("Successfully saved {} products from Kakao Store", productRequests.size());

        } catch (Exception e) {
            log.error("Error during crawling and saving Kakao Store deals", e);
        }
    }
}