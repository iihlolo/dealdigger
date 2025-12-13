package com.dealdigger.service;

import com.dealdigger.domain.Product;
import com.dealdigger.dto.CreateProductRequest;
import com.dealdigger.dto.ProductResponse;
import com.dealdigger.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        Product p = Product.builder()
                .productName(req.getProductName())
                .imageUrl(req.getImageUrl())
                .originalPrice(req.getOriginalPrice())
                .discountedPrice(req.getDiscountedPrice())
                .linkPath(req.getLinkPath())
                .deliveryFeeType(req.getDeliveryFeeType())
                .hasAdditionalOptionPrice(req.isHasAdditionalOptionPrice())
                .discountStartDate(req.getDiscountStartDate())
                .discountEndDate(req.getDiscountEndDate())
                .liked(false)
                .build();

        return toResponse(productRepository.save(p));
    }

    /**
     * Read
     * Find all products
     */
    public List<ProductResponse> findAll() {
        return productRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    /** 
     * Read
     * Search products by keyword
     */
    public List<ProductResponse> searchByKeyword(String keyword) {
        return productRepository.findByProductNameContainingIgnoreCase(keyword)
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

        p.setLiked(!p.isLiked());

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
                .productName(p.getProductName())
                .imageUrl(p.getImageUrl())
                .originalPrice(p.getOriginalPrice())
                .discountedPrice(p.getDiscountedPrice())
                .linkPath(p.getLinkPath())
                .deliveryFeeType(p.getDeliveryFeeType())
                .hasAdditionalOptionPrice(p.isHasAdditionalOptionPrice())
                .discountEndDate(p.getDiscountEndDate())
                .liked(p.isLiked())
                .build();
    }
}