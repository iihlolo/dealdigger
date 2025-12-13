package com.dealdigger.crawler;

import com.dealdigger.dto.CreateProductRequest;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class KakaoDealParser {

    private static final String KAKAO_STORE_BASE_URL = "https://store.kakao.com";
    private static final DateTimeFormatter KAKAO_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public List<CreateProductRequest> parse(JsonNode products) {
        if (products == null || !products.isArray()) {
            return Collections.emptyList();
        }

        List<CreateProductRequest> list = new ArrayList<>();

        for (JsonNode p : products) {
            try {
                int discountedPrice = p.has("maxDiscountedPrice")
                        ? p.get("maxDiscountedPrice").asInt()
                        : p.path("groupDiscountedPrice").asInt(0);

                String rawLinkPath = p.path("linkPath").asText("");
                String linkPath = KAKAO_STORE_BASE_URL + rawLinkPath;

                JsonNode periodNode = p.path("groupDiscountPeriod");
                String start = periodNode.path("from").asText();
                String end = periodNode.path("to").asText();

                LocalDateTime discountStartDate = LocalDateTime.parse(start, KAKAO_DATE_FORMATTER);
                LocalDateTime discountEndDate = LocalDateTime.parse(end, KAKAO_DATE_FORMATTER);

                CreateProductRequest req = CreateProductRequest.builder()
                        .productName(p.path("productName").asText(""))
                        .imageUrl(p.path("productImageOrigin").asText(""))
                        .originalPrice(p.path("originalPrice").asInt(0))
                        .discountedPrice(discountedPrice)
                        .linkPath(linkPath)
                        .deliveryFeeType(p.path("deliveryFeeType").asText(""))
                        .hasAdditionalOptionPrice(p.path("hasAdditionalOptionPrice").asBoolean(false))
                        .discountStartDate(discountStartDate)
                        .discountEndDate(discountEndDate)
                        .build();

                list.add(req);

            } catch (Exception e) {
                log.error("Failed to parse a product", e);
            }
        }

        return list;
    }
}