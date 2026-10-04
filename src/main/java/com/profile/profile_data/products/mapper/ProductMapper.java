package com.profile.profile_data.products.mapper;

import com.profile.profile_data.products.dto.ProductRequest;
import com.profile.profile_data.products.dto.ProductResponse;
import com.profile.profile_data.products.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toModel(ProductRequest request) {
        Product product = new Product();
        product.setImage(request.image());
        product.setDescription(request.description());
        product.setFeedback(request.feedback());
        product.setPrice(request.price());

        return product;
    }


    public ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getImage(),
                product.getDescription(),
                product.getFeedback(),
                product.getPrice()
        );
    }
}
