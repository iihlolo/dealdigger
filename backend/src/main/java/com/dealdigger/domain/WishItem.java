package com.dealdigger.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("wishitems")
@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
public class WishItem {

    @Id
    private String id;

    @Indexed(unique = true)
    private String keyword;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}