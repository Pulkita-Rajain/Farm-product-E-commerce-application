package com.app.ecom.dto;

import lombok.Data;

@Data
public class ProductRequest {
    private String name;
    private String description;
    private java.math.BigDecimal price;
    private Integer stockQuantity;
    private String category;
    private String imageUrl;


}
