package com.profile.profile_data.products.controller;

import com.profile.profile_data.common.exception.ApiExceptionHandler;
import com.profile.profile_data.products.dto.ProductRequest;
import com.profile.profile_data.products.dto.ProductResponse;
import com.profile.profile_data.products.exception.ProductNotFoundException;
import com.profile.profile_data.products.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ProductController(productService))
                .setControllerAdvice(new ApiExceptionHandler())
                .build();
    }

    @Test
    void findAllReturnsProducts() throws Exception {
        when(productService.findAll()).thenReturn(List.of(product()));

        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("product-1"))
                .andExpect(jsonPath("$[0].price").value(12.5));
    }

    @Test
    void findByIdReturnsProduct() throws Exception {
        when(productService.findById("product-1")).thenReturn(product());

        mockMvc.perform(get("/api/v1/products/product-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("product-1"));
    }

    @Test
    void findByIdReturnsNotFoundForUnknownProduct() throws Exception {
        when(productService.findById("missing"))
                .thenThrow(new ProductNotFoundException("missing"));

        mockMvc.perform(get("/api/v1/products/missing"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Product not found"));
    }

    @Test
    void createReturnsCreatedProduct() throws Exception {
        when(productService.create(any(ProductRequest.class))).thenReturn(product());

        mockMvc.perform(post("/api/v1/products")
                        .contentType(APPLICATION_JSON)
                        .content(validRequestJson()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("product-1"))
                .andExpect(jsonPath("$.feedback").value(5));
    }

    @Test
    void createRejectsInvalidProductRequest() throws Exception {
        String invalidRequest = """
                {
                  "image": " ",
                  "description": "",
                  "feedback": -1,
                  "price": 0
                }
                """;

        mockMvc.perform(post("/api/v1/products")
                        .contentType(APPLICATION_JSON)
                        .content(invalidRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Invalid request"))
                .andExpect(jsonPath("$.errors.image").exists())
                .andExpect(jsonPath("$.errors.description").exists())
                .andExpect(jsonPath("$.errors.feedback").exists())
                .andExpect(jsonPath("$.errors.price").exists());
    }

    @Test
    void updateReturnsUpdatedProduct() throws Exception {
        when(productService.update(any(String.class), any(ProductRequest.class)))
                .thenReturn(product());

        mockMvc.perform(put("/api/v1/products/product-1")
                        .contentType(APPLICATION_JSON)
                        .content(validRequestJson()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("product-1"));
    }

    @Test
    void deleteReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/products/product-1"))
                .andExpect(status().isNoContent());

        verify(productService).delete("product-1");
    }

    private ProductResponse product() {
        return new ProductResponse(
                "product-1",
                "image.png",
                "A product",
                5,
                new BigDecimal("12.50")
        );
    }

    private String validRequestJson() {
        return """
                {
                  "image": "image.png",
                  "description": "A product",
                  "feedback": 5,
                  "price": 12.50
                }
                """;
    }
}
