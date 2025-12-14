package com.dealdigger.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class WishRequest {
    
    @NotBlank
    private String keyword;
    
    @Min(0)
    @Max(100)
    private int desiredDiscountRate;
}