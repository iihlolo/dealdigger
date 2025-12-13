package com.dealdigger.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Builder
@AllArgsConstructor @NoArgsConstructor
public class ProductResponse {
    
    private String id;
    private String productName;
    private String imageUrl;
    private int originalPrice;
    private int discountedPrice;
    private String linkPath;
    private String deliveryFeeType;
    private boolean hasAdditionalOptionPrice;
    private LocalDateTime discountEndDate;
    private boolean liked; 
}