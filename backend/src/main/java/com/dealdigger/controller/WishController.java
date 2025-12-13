package com.dealdigger.controller;

import com.dealdigger.dto.WishRequest;
import com.dealdigger.dto.WishResponse;
import com.dealdigger.service.WishService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishes")
@RequiredArgsConstructor
public class WishController {

    private final WishService wishService;

    @PostMapping
    public WishResponse create(@RequestBody WishRequest req) {
        return wishService.save(req);
    }

    @GetMapping
    public List<WishResponse> all() {
        return wishService.findAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        wishService.delete(id);
    }
}