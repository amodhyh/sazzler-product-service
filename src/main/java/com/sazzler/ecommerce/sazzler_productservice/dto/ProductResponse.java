package com.sazzler.ecommerce.sazzler_productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ProductResponse {
    private Long id;
    private String description;
    private Double price;
}
