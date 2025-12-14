package com.dealdigger.service;

import com.dealdigger.domain.Product;
import com.dealdigger.domain.WishItem;
import com.dealdigger.dto.WishRequest;
import com.dealdigger.dto.WishResponse;
import com.dealdigger.repository.ProductRepository;
import com.dealdigger.repository.WishRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class WishService {

    private final WishRepository wishRepository;
    private final ProductRepository productRepository;

    /**
     * Create
     * Add a new wish item
     */
    public WishResponse save(WishRequest req) {
        wishRepository.findByKeyword(req.getKeyword())
                .ifPresent(w -> {
                    throw new IllegalArgumentException("Wish item already exists for keyword: " + req.getKeyword());
                });

        WishItem w = WishItem.builder()
                .keyword(req.getKeyword())
                .desiredDiscountRate(req.getDesiredDiscountRate())
                .build();

        return toResponse(wishRepository.save(w));
    }

    /**
     * Read
     * Find all wish items
     */
    public List<WishResponse> findAll() {
        return wishRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Read
     * Find a wish item by ID
     */
    public WishResponse findById(String id) {
        WishItem wish = wishRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Wish not found: " + id));
        return toResponse(wish);
    }
    
    /**
     * Delete
     * Remove a wish item by ID
     */
    @Transactional
    public void delete(String wishId) {
        WishItem wish = wishRepository.findById(wishId)
                .orElseThrow(() ->
                        new NoSuchElementException("Wish item not found for ID: " + wishId));

        List<Product> products = productRepository.findByMatchedWishIds(wishId);

        for (Product product : products) {
            product.removeWish(wishId);

            if (product.getMatchedWishIds().isEmpty()) {
                productRepository.delete(product);
            } else {
                productRepository.save(product);
            }
        }

        wishRepository.delete(wish);
    }

    /**
     * Convert
     * Entity -> DTO
     */
    private WishResponse toResponse(WishItem w) {
        return WishResponse.builder()
                .id(w.getId())
                .keyword(w.getKeyword())
                .desiredDiscountRate(w.getDesiredDiscountRate())
                .build();
    }
}