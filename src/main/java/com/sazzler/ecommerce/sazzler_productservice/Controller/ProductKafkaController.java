package com.sazzler.ecommerce.sazzler_productservice.Controller;

import com.sazzler.ecommerce.sazzler_api_def.product_service.DTO.ProductEvent;
import com.sazzler.ecommerce.sazzler_productservice.Service.ProductEventProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@Slf4j
@RequiredArgsConstructor
public class ProductKafkaController {
    private final ProductEventProducerService productEventProducerService;

    @PostMapping("/kafka/send")
    public ResponseEntity<Void> sendMessage(
               @RequestParam("key") String key, // The partition key can stay as a param
               @RequestBody ProductEvent productEvent // The DTO comes in the request body
    ) {
        log.info("Received request to send event for Product ID: {}", productEvent.productId());

        // Validation for Objects
        if (key == null || key.isBlank() ) {
            log.warn("Invalid key or product event provided");
            return ResponseEntity.badRequest().build();
        }

        // Pass the object to the service
        productEventProducerService.sendMessage(key, productEvent);

        log.info("Event successfully handed off to producer for key: {}", key);
        return ResponseEntity.accepted().build();
    }
}
