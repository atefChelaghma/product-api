package com.profile.profile_data.products.repository;

import com.profile.profile_data.products.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
