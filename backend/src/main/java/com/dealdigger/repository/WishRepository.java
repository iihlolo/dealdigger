package com.dealdigger.repository;

import com.dealdigger.domain.WishItem;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishRepository extends MongoRepository<WishItem, String> {
}