package com.profile.profile_data.products.service;

import com.profile.profile_data.products.dto.ProductRequest;
import com.profile.profile_data.products.dto.ProductResponse;

import java.util.List;
public interface ProductService {

    List<ProductResponse> findAll();

    ProductResponse findById(String id);

    ProductResponse create(ProductRequest request);

    ProductResponse update(String id, ProductRequest request);

    void delete(String  id);
}
