package com.sazzler.ecommerce.sazzler_productservice.Service;

import org.springframework.stereotype.Service;

@Service
public class ProductService {
    public void createProduct(ProductRequest productRequest) {
        // Logic to create a product will go here
        System.out.println("Product created successfully");
    }
}
