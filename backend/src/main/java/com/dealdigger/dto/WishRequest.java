package com.dealdigger.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class WishRequest {
    
    @NotBlank
    private String keyword;
}