package com.dealdigger.service;

import com.dealdigger.domain.WishItem;
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

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 100;

    private final KakaoCrawler kakaoCrawler;
    private final KakaoDealParser kakaoDealParser;
    private final ProductService productService;

    /**
     * Crawl and Save Kakao Store Deals
     */
    public void crawlAndSaveKakaoDeals(WishItem wish) {
        try {
            String response = kakaoCrawler.crawl(wish.getKeyword(), DEFAULT_PAGE, DEFAULT_SIZE);
            if (response == null || response.isBlank()) return;

            List<CreateProductRequest> parsed = kakaoDealParser.parse(response);
            if (parsed.isEmpty()) return;

            List<CreateProductRequest> filtered = parsed.stream()
                    .filter(p -> p.getDiscountRate() >= wish.getDesiredDiscountRate())
                    .map(p -> injectMatchedWishId(p, wish.getId()))
                    .toList();
            if (filtered.isEmpty()) return;

            filtered.forEach(productService::save);

        } catch (Exception e) {
            log.error("Failed to crawl and save Kakao deals. wishId={}, keyword={}", wish.getId(), wish.getKeyword(), e);
        }
    }

    private CreateProductRequest injectMatchedWishId(CreateProductRequest req, String wishId) {
        return CreateProductRequest.builder()
                .source(req.getSource())
                .externalProductId(req.getExternalProductId())
                .productName(req.getProductName())
                .imageUrl(req.getImageUrl())
                .productUrl(req.getProductUrl())
                .originalPrice(req.getOriginalPrice())
                .discountedPrice(req.getDiscountedPrice())
                .discountRate(req.getDiscountRate())
                .hasAdditionalOptionPrice(req.isHasAdditionalOptionPrice())
                .freeDelivery(req.isFreeDelivery())
                .discountStartDate(req.getDiscountStartDate())
                .discountEndDate(req.getDiscountEndDate())
                .matchedWishId(wishId)
                .build();
    }
}