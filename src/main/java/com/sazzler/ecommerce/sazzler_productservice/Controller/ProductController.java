package com.sazzler.ecommerce.sazzler_productservice.Controller;

import com.sazzler.ecommerce.sazzler_productservice.Service.ProductService;
import com.sazzler.ecommerce.sazzler_productservice.dto.ProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
public class ProductController {
final private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(value = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createProduct(@RequestBody ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    }

}
