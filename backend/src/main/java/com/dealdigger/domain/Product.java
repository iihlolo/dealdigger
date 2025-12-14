package com.dealdigger.domain;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Document("products")
@Getter @Builder
@AllArgsConstructor @NoArgsConstructor
public class Product {

    @Id
    private String id;

    @Indexed
    private ProductSource source;
   
    @Indexed(unique = true)
    private String externalProductId;

    @Indexed
    private String productName;

    private String imageUrl;
    private String productUrl;

    private int originalPrice;
    private int discountedPrice;
    private int discountRate;

    private boolean hasAdditionalOptionPrice;
    private boolean freeDelivery;

    private LocalDateTime discountStartDate;
    
    @Indexed
    private LocalDateTime discountEndDate;

    private boolean liked;

    @Indexed
    @Builder.Default
    private Set<String> matchedWishIds = new HashSet<>();

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    public void addWish(String wishId) {
        matchedWishIds.add(wishId);
    }

    public void removeWish(String wishId) {
        matchedWishIds.remove(wishId);
    }

    public boolean hasNoMatchedWishes() {
        return matchedWishIds.isEmpty();
    }

    public void toggleLike() {
        this.liked = !this.liked;
    }
}