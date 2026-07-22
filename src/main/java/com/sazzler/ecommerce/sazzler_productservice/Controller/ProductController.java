package com.sazzler.ecommerce.sazzler_productservice.Controller;

import com.sazzler.ecommerce.sazzler_api_def.product_service.DTO.ProductEvent;
import com.sazzler.ecommerce.sazzler_api_def.product_service.DTO.ProductRequest;
import com.sazzler.ecommerce.sazzler_productservice.Service.ProductEventProducerService;
import com.sazzler.ecommerce.sazzler_productservice.Service.ProductRetrieveService;
import com.sazzler.ecommerce.sazzler_productservice.Service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/product")
public class ProductController  {
    private final ProductService productService;
    private final ProductRetrieveService productRetrieveService;
    private final ProductEventProducerService productEventProducerService;

    @PostMapping("/kafka/send")
    public ResponseEntity<Void> sendMessage(
            @RequestParam("key") String key, // The partition key can stay as a param
            @RequestBody ProductEvent productEvent // The DTO comes in the request body
    ) {
        // Validation for Objects
        if (key == null || key.isBlank() || productEvent == null) {
            log.warn("Invalid key or product event provided");
            return ResponseEntity.badRequest().build();
        }

        log.info("Received request to send event for Product ID: {}", productEvent.productId());

        // Pass the object to the service
        productEventProducerService.sendMessage(key, productEvent);

        log.info("Event successfully handed off to producer for key: {}", key);
        return ResponseEntity.accepted().build();
    }


    @PostMapping(value = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    public String createProduct(@RequestBody ProductRequest productRequest) {
        // You may want to call productService.createProduct here and return a String result
        return productService.createProduct(productRequest);
    }
//
    @GetMapping(value = "/products")
    public String getProducts() {
        // You may want to call productRetrieveService.getProducts and convert result to String
        return Objects.requireNonNull(productRetrieveService.getProducts().getBody()).toString();
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("UP");
    }


}
