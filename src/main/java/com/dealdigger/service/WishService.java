package com.dealdigger.service;

import com.dealdigger.domain.WishItem;
import com.dealdigger.dto.WishRequest;
import com.dealdigger.dto.WishResponse;
import com.dealdigger.repository.WishRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishService {

    private final WishRepository wishRepository;

    /**
     * Create
     * Add a new wish item
     */
    public WishResponse save(WishRequest req) {
        WishItem w = WishItem.builder()
                .keyword(req.getKeyword())
                .build();

        return toResponse(wishRepository.save(w));
    }

    /**
     * Read
     * Find all products
     */
    public List<WishResponse> findAll() {
        return wishRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Delete
     * Remove a wish item by ID
     */
    public void delete(String wishId) {
        wishRepository.deleteById(wishId);
    }

    /**
     * Convert
     * Entity -> DTO
     */
    private WishResponse toResponse(WishItem w) {
        return WishResponse.builder()
                .id(w.getId())
                .keyword(w.getKeyword())
                .build();
    }
}