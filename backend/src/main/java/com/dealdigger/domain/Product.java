package com.dealdigger.domain;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("products")
@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
public class Product {

    @Id
    private String id;
    
    @Indexed
    private String productName;
    
    private String imageUrl;

    private int originalPrice;
    private int discountedPrice;

    private String linkPath;

    private String deliveryFeeType;
    private boolean hasAdditionalOptionPrice;

    private LocalDateTime discountStartDate;
    private LocalDateTime discountEndDate;

    private boolean liked;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}