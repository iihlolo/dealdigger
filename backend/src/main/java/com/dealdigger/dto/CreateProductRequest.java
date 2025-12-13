package com.dealdigger.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
public class CreateProductRequest {
    
    private String productName;
    private String imageUrl;
    private int originalPrice;
    private int discountedPrice;
    private String linkPath;
    private String deliveryFeeType;
    private boolean hasAdditionalOptionPrice;
    private LocalDateTime discountStartDate;
    private LocalDateTime discountEndDate;
}