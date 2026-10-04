package com.profile.profile_data.products.service;

import com.profile.profile_data.products.dto.ProductRequest;
import com.profile.profile_data.products.dto.ProductResponse;
import com.profile.profile_data.products.exception.ProductNotFoundException;
import com.profile.profile_data.products.mapper.ProductMapper;
import com.profile.profile_data.products.model.Product;
import com.profile.profile_data.products.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    private ProductServiceImpl productService;

    private final ProductMapper productMapper = new ProductMapper();

    @BeforeEach
    void setUp() {
        productService = new ProductServiceImpl(productRepository, productMapper);
    }

    @Test
    void findAllMapsProductsToResponses() {
        Product product = product("1");
        when(productRepository.findAll()).thenReturn(List.of(product));

        List<ProductResponse> responses = productService.findAll();

        assertEquals(List.of(productMapper.toResponse(product)), responses);
    }

    @Test
    void findByIdReturnsProductResponse() {
        Product product = product("1");
        when(productRepository.findById("1")).thenReturn(Optional.of(product));

        ProductResponse response = productService.findById("1");

        assertEquals(productMapper.toResponse(product), response);
    }

    @Test
    void findByIdThrowsWhenProductDoesNotExist() {
        when(productRepository.findById("missing")).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.findById("missing"));
    }

    @Test
    void createSavesAndReturnsProduct() {
        ProductRequest request = request();
        Product savedProduct = product("1");
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        ProductResponse response = productService.create(request);

        assertEquals(productMapper.toResponse(savedProduct), response);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void updateSavesChangedProduct() {
        Product existing = product("1");
        ProductRequest request = new ProductRequest(
                "new-image",
                "New description",
                8,
                new BigDecimal("25.99")
        );
        when(productRepository.findById("1")).thenReturn(Optional.of(existing));
        when(productRepository.save(existing)).thenReturn(existing);

        ProductResponse response = productService.update("1", request);

        assertEquals("new-image", response.image());
        assertEquals("New description", response.description());
        assertEquals(8, response.feedback());
        assertEquals(new BigDecimal("25.99"), response.price());
    }

    @Test
    void updateThrowsWhenProductDoesNotExist() {
        when(productRepository.findById("missing")).thenReturn(Optional.empty());

        assertThrows(
                ProductNotFoundException.class,
                () -> productService.update("missing", request())
        );
    }

    @Test
    void deleteThrowsWhenProductDoesNotExist() {
        when(productRepository.existsById("missing")).thenReturn(false);

        assertThrows(ProductNotFoundException.class, () -> productService.delete("missing"));
    }

    @Test
    void deleteRemovesExistingProduct() {
        when(productRepository.existsById("1")).thenReturn(true);

        productService.delete("1");

        verify(productRepository).deleteById("1");
    }

    private Product product(String id) {
        return new Product(id, "image", "Description", 5, new BigDecimal("12.50"));
    }

    private ProductRequest request() {
        return new ProductRequest("image", "Description", 5, new BigDecimal("12.50"));
    }
}
