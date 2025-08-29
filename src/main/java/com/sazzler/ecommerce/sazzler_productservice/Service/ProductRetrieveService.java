package com.sazzler.ecommerce.sazzler_productservice.Service;

import com.sazzler.ecommerce.ExceptionHandlers.ProductNotFound;
import com.sazzler.ecommerce.sazzler_productservice.Entity.Product;
import com.sazzler.ecommerce.sazzler_productservice.Repository.ProductRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ProductRetrieveService {

    @Autowired
    private final ProductRepo productRepo;

    public ResponseEntity<List<Product>> getProducts() {
        List<Product> products = productRepo.findAll();
       if(products.isEmpty()){
           throw new ProductNotFound("No products available");
       }
       else  {
           return ResponseEntity.ok(products);
       }
    }
}
