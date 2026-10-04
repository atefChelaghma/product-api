package com.profile.profile_data.products.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;

public record ProductRequest(

        @NotBlank
        String image,

        @NotBlank
        String description,

        @NotNull
        @PositiveOrZero
        Integer feedback,

        @NotNull
        @DecimalMin(value = "0.0", inclusive = false)
        BigDecimal price
) {
}
