package com.sazzler.ecommerce.sazzler_productservice.Service;

import com.sazzler.ecommerce.sazzler_api_def.product_service.DTO.ProductEvent;
import com.sazzler.ecommerce.sazzler_api_def.product_service.DTO.ProductEventType;
import com.sazzler.ecommerce.sazzler_api_def.product_service.DTO.ProductRequest;
import com.sazzler.ecommerce.sazzler_productservice.Entity.Product;
import com.sazzler.ecommerce.sazzler_productservice.Exceptions.ProductIDAlreadyExists;
import com.sazzler.ecommerce.sazzler_productservice.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ProductService {
    private final ProductRepo productRepo;
    private final ProductEventProducerService productEventProducerService;

    @Autowired
    public ProductService(ProductRepo productRepo, ProductEventProducerService productEventProducerService) {
        this.productRepo = productRepo;
        this.productEventProducerService = productEventProducerService;
    }

    @Transactional
    public String createProduct(ProductRequest productRequest) {
        if (!productRepo.existsById(productRequest.id())) {
            Product product = Product.builder()
                    .creationDate(LocalDateTime.now())
                    .id(productRequest.id())
                    .price(productRequest.price())
                    .name(productRequest.name())
                    .build();
            productRepo.save(product);

            ProductEvent event = new ProductEvent(
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    ProductEventType.CREATED
            );
            if (org.springframework.transaction.support.TransactionSynchronizationManager.isActualTransactionActive()) {
                org.springframework.transaction.support.TransactionSynchronizationManager.registerSynchronization(
                    new org.springframework.transaction.support.TransactionSynchronization() {
                        @Override
                        public void afterCommit() {
                            productEventProducerService.sendMessage(String.valueOf(product.getId()), event);
                        }
                    }
                );
            } else {
                productEventProducerService.sendMessage(String.valueOf(product.getId()), event);
            }

            return "Product Created Successfully! ";
        } else {
            throw new ProductIDAlreadyExists("Product ID " + productRequest.id() + " Already Exists");
        }
    }
}
