package com.dealdigger.service;

import com.dealdigger.domain.Product;
import com.dealdigger.dto.CreateProductRequest;
import com.dealdigger.dto.ProductResponse;
import com.dealdigger.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * Create
     * Add a new product
     */
    public ProductResponse save(CreateProductRequest req) {
        Product p = productRepository
                .findBySourceAndExternalProductId(
                        req.getSource(),
                        req.getExternalProductId()
                )
                .orElseGet(() -> Product.builder()
                        .source(req.getSource())
                        .externalProductId(req.getExternalProductId())
                        .productName(req.getProductName())
                        .imageUrl(req.getImageUrl())
                        .productUrl(req.getProductUrl())
                        .originalPrice(req.getOriginalPrice())
                        .discountedPrice(req.getDiscountedPrice())
                        .discountRate(req.getDiscountRate())
                        .hasAdditionalOptionPrice(req.isHasAdditionalOptionPrice())
                        .freeDelivery(req.isFreeDelivery())
                        .discountStartDate(req.getDiscountStartDate())
                        .discountEndDate(req.getDiscountEndDate())
                        .liked(false)
                        .build()
            );

        p.addWish(req.getMatchedWishId());
        return toResponse(productRepository.save(p));
    }

    /** 
     * Read
     * Find products by wish ID
     */
    public List<ProductResponse> findByWish(String wishId) {
        return productRepository.findByMatchedWishIds(wishId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Read
     * Find today's added products
     */
    public List<ProductResponse> findTodayProducts() {
        LocalDateTime startOfToday = LocalDate.now().atStartOfDay();

        return productRepository.findByCreatedAtAfter(startOfToday)
                .stream()
                .map(this::toResponse)
                .toList();
    }


    /**
     * Update
     * Toggle like status
     */
    public ProductResponse toggleLike(String id) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        p.toggleLike();

        return toResponse(productRepository.save(p));
    }

    /**
     * Delete
     * Remove expired products
     */
    public void deleteExpired() {
        productRepository.deleteByDiscountEndDateBefore(LocalDateTime.now());
    }

    /**
     * Convert
     * Entity -> DTO
     */
    private ProductResponse toResponse(Product p) {
        return ProductResponse.builder()
                .id(p.getId())
                .source(p.getSource())
                .externalProductId(p.getExternalProductId())
                .productName(p.getProductName())
                .imageUrl(p.getImageUrl())
                .productUrl(p.getProductUrl())
                .originalPrice(p.getOriginalPrice())
                .discountedPrice(p.getDiscountedPrice())
                .discountRate(p.getDiscountRate())
                .hasAdditionalOptionPrice(p.isHasAdditionalOptionPrice())
                .freeDelivery(p.isFreeDelivery())
                .discountEndDate(p.getDiscountEndDate())
                .liked(p.isLiked())
                .build();
    }
}