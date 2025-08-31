package com.sazzler.ecommerce.sazzler_productservice.Service;

import com.sazzler.ecommerce.sazzler_productservice.Entity.Product;
import com.sazzler.ecommerce.sazzler_productservice.Exceptions.ProductIDAlreadyExists;
import com.sazzler.ecommerce.sazzler_productservice.Repository.ProductRepo;
import com.sazzler.ecommerce.product_service.DTO.ProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ProductService {
    final private ProductRepo productRepo;

    @Autowired
    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @Transactional
    public ResponseEntity<String> createProduct(ProductRequest productRequest) {
        if(productRepo.findById(productRequest.id())==null){
           Product temp= Product.builder()
                    .creationDate(LocalDateTime.now())
                    .id(productRequest.id())
                    .price(productRequest.price())
                    .name(productRequest.name())
                    .build();
            productRepo.save(temp);
            return new ResponseEntity<>("Product Created Successfully! ",HttpStatus.CREATED);
        }
        else {
            throw new ProductIDAlreadyExists("Product ID "+productRequest.id()+"Already Exists");
        }

    }
}
