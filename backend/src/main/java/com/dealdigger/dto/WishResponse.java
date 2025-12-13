package com.dealdigger.dto;

import lombok.*;

@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
public class WishResponse {
    
    private String id;
    private String keyword;
}