package com.example.E_com.proj.Dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductListDTO {
    private int id;
    private String name;
    private String description;
    private BigDecimal price;

    private String imageUrl;  // frontend-ready base64 image string
}
