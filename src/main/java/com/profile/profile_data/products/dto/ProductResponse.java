package com.profile.profile_data.products.dto;

import java.math.BigDecimal;

public record ProductResponse(
        String id,
        String image,
        String description,
        Integer feedback,
        BigDecimal price
) {
}
