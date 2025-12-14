package com.dealdigger.controller;

import com.dealdigger.dto.WishRequest;
import com.dealdigger.dto.WishResponse;
import com.dealdigger.service.WishService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/wishes")
@RequiredArgsConstructor
public class WishController {

    private final WishService wishService;

    /**
     * Create
     * Add a new wish item
     */
    @PostMapping
    public WishResponse create(@Valid @RequestBody WishRequest req) {
        return wishService.save(req);
    }

    /**
     * Read
     * Find all wish items
     */
    @GetMapping
    public List<WishResponse> all() {
        return wishService.findAll();
    }

    /**
     * Read
     * Find a wish item by ID
     */
    @GetMapping("/{id}")
    public WishResponse get(@PathVariable String id) {
        return wishService.findById(id);
    }

    /**
     * Delete
     * Remove a wish item by ID
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        wishService.delete(id);
    }
}