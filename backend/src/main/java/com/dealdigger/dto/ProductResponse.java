package com.dealdigger.dto;

import com.dealdigger.domain.ProductSource;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Builder
@AllArgsConstructor @NoArgsConstructor
public class ProductResponse {
    
    private String id;
    private ProductSource source;
    private String externalProductId;
    private String productName;
    private String imageUrl;
    private String productUrl;
    private int originalPrice;
    private int discountedPrice;
    private int discountRate;
    private boolean hasAdditionalOptionPrice;
    private boolean freeDelivery;
    private LocalDateTime discountEndDate;
    private boolean liked;
}