package com.dealdigger.crawler;

import com.dealdigger.domain.ProductSource;
import com.dealdigger.dto.CreateProductRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoDealParser {

    private static final String KAKAO_STORE_BASE_URL = "https://store.kakao.com";

    public List<CreateProductRequest> parse(String responseBody) throws JsonProcessingException{
        
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(responseBody);
        
        JsonNode contents = root.path("data").path("contents");
        if (!contents.isArray()) {
            return List.of();
        }

        LocalDateTime now = LocalDateTime.now();

        List<CreateProductRequest> results = new ArrayList<>();

        for (JsonNode item : contents) {
            String imageUrl = item.hasNonNull("productImageOrigin")
                    ? item.get("productImageOrigin").asText()
                    : item.get("productImage").asText();

            CreateProductRequest req = CreateProductRequest.builder()
                    .source(ProductSource.KAKAO_STORE)
                    .externalProductId(item.get("productId").asText())
                    .productName(item.get("productName").asText())
                    .imageUrl(imageUrl)
                    .productUrl(KAKAO_STORE_BASE_URL + item.get("linkPath").asText())
                    .originalPrice(item.get("originalPrice").asInt())
                    .discountedPrice(item.get("discountedPrice").asInt())
                    .discountRate(item.get("discountRate").asInt())
                    .hasAdditionalOptionPrice(item.get("hasAdditionalOptionPrice").asBoolean(false))
                    .freeDelivery(item.get("freeDelivery").asBoolean(false))
                    .discountStartDate(now)
                    .discountEndDate(now.plusDays(1))
                    .matchedWishId(null)
                    .build();

                results.add(req);
        }

        return results;
    }
}