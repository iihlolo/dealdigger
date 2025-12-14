package com.dealdigger.dto;

import lombok.*;

@Getter @Builder
@AllArgsConstructor @NoArgsConstructor
public class WishResponse {
    
    private String id;
    private String keyword;
    private int desiredDiscountRate;
}