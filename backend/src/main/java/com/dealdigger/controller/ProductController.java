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
     * Find all products
     */
    @GetMapping
    public List<ProductResponse> all() {
        return productService.findAll();
    }

    /**
     * Read
     * Search products by keyword
     */
    @GetMapping("/search")
    public List<ProductResponse> search(@RequestParam String keyword) {
        return productService.searchByKeyword(keyword);
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