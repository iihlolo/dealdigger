package com.dealdigger.controller;

import com.dealdigger.dto.ProductResponse;
import com.dealdigger.service.ProductService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * Read
     * Find products by wish ID
     */
    @GetMapping("/by-wish/{wishId}")
    public List<ProductResponse> findByWish(@PathVariable String wishId) {
        return productService.findByWish(wishId);
    }
   
    /**
     * Read
     * Find today's added products
     */
    @GetMapping("/today")
    public List<ProductResponse> today() {
        return productService.findTodayProducts();
    }

    /**
     * Update
     * Toggle like status
     */
    @PostMapping("/{id}/like")
    public ProductResponse toggleLike(@PathVariable String id) {
        return productService.toggleLike(id);
    }
}