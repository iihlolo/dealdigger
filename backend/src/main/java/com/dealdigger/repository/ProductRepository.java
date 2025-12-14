package com.dealdigger.repository;

import com.dealdigger.domain.Product;
import com.dealdigger.domain.ProductSource;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    Optional<Product> findBySourceAndExternalProductId(ProductSource source, String externalProductId);

    List<Product> findByMatchedWishIds(String wishId);

    List<Product> findByCreatedAtAfter(LocalDateTime start);

    void deleteByDiscountEndDateBefore(LocalDateTime now);
}