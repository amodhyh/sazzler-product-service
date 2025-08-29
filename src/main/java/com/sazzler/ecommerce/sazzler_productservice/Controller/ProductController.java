package com.sazzler.ecommerce.sazzler_productservice.Controller;

import com.sazzler.ecommerce.sazzler_productservice.Service.ProductRetrieveService;
import com.sazzler.ecommerce.sazzler_productservice.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
public class ProductController  {
    private final ProductService productService;
    private final ProductRetrieveService productRetrieveService;

    @Autowired
    public ProductController(ProductService productService, ProductRetrieveService productRetrieveService) {
        this.productService = productService;
        this.productRetrieveService = productRetrieveService;
    }
//
//    @PostMapping(value = "/create")
//    @ResponseStatus(HttpStatus.CREATED)
//    public String createProduct(@RequestBody String productRequest) {
//        // You may want to call productService.createProduct here and return a String result
//        return productService.createProduct(productRequest).getBody();
//    }
//
//    @GetMapping(value = "/products")
//    public String getProducts() {
//        // You may want to call productRetrieveService.getProducts and convert result to String
//        return productRetrieveService.getProducts().getBody().toString();
//    }


}
