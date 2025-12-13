package com.dealdigger.repository;

import com.dealdigger.domain.Product;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    List<Product> findByProductNameContainingIgnoreCase(String keyword);

    void deleteByDiscountEndDateBefore(LocalDateTime now);
}