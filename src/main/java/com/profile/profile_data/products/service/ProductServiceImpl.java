package com.profile.profile_data.products.service;

import com.profile.profile_data.products.dto.ProductRequest;
import com.profile.profile_data.products.dto.ProductResponse;
import com.profile.profile_data.products.mapper.ProductMapper;
import com.profile.profile_data.products.model.Product;
import com.profile.profile_data.products.repository.ProductRepository;
import com.profile.profile_data.products.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(
            ProductRepository productRepository,
            ProductMapper productMapper
    ) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public List<ProductResponse> findAll() {

        return productRepository.findAll()
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }

    @Override
    public ProductResponse findById(String id) {
        return productMapper.toResponse(findProductOrThrow(id));
    }

    @Override
    public ProductResponse create(ProductRequest request) {

        Product product = productMapper.toModel(request);

        Product savedProduct = productRepository.save(product);

        return productMapper.toResponse(savedProduct);
    }

    @Override
    public ProductResponse update(
            String id,
            ProductRequest request
    ) {

        Product product = findProductOrThrow(id);

        product.setImage(request.image());
        product.setDescription(request.description());
        product.setFeedback(request.feedback());
        product.setPrice(request.price());

        Product updatedProduct = productRepository.save(product);

        return productMapper.toResponse(updatedProduct);
    }

    @Override
    public void delete(String id) {

        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }

        productRepository.deleteById(id);
    }

    private Product findProductOrThrow(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }
}