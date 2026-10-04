package com.profile.profile_data.products.controller;

import com.profile.profile_data.products.dto.ProductRequest;
import com.profile.profile_data.products.dto.ProductResponse;
import com.profile.profile_data.products.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Products", description = "Manage products in the product catalog")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @Operation(summary = "List products", description = "Returns all products in the catalog.")
    public List<ProductResponse> findAll() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a product", description = "Returns the product with the specified ID.")
    public ProductResponse findById(
            @PathVariable String id
    ) {
        return productService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a product", description = "Creates a product and returns the saved product.")
    public ProductResponse create(
            @Valid @RequestBody ProductRequest request
    ) {
        return productService.create(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a product", description = "Replaces the product fields for the specified ID.")
    public ProductResponse update(
            @PathVariable String id,
            @Valid @RequestBody ProductRequest request
    ) {
        return productService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a product", description = "Deletes the product with the specified ID.")
    public void delete(
            @PathVariable String id
    ) {
        productService.delete(id);
    }
}