package com.profile.profile_data.products.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "products")
public class Product {
    @Id
    private String id;
    private String image;
    private String description;
    private Integer feedback;
    private BigDecimal price;

    public Product() {
    }

    public Product(
            String id,
            String image,
            String description,
            Integer feedback,
            BigDecimal price
    ) {
        this.id = id;
        this.image = image;
        this.description = description;
        this.feedback = feedback;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public Integer getFeedback() {
        return feedback;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFeedback(Integer feedback) {
        this.feedback = feedback;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
