package com.sazzler.ecommerce.sazzler_productservice.Controller;

import com.sazzler.ecommerce.sazzler_productservice.Service.MessageProducerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductKafkaController {
    private final MessageProducerService messageProducerService;

    public ProductKafkaController(MessageProducerService messageProducerService) {
        this.messageProducerService = messageProducerService;
    }

   @PostMapping("/kafka/send")
    public ResponseEntity<Void> sendMessage(@RequestParam("key") String key,
                                            @RequestParam("message") String message) {
        if (key == null || key.isBlank() || message == null || message.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        messageProducerService.sendMessage(key, message);
        return ResponseEntity.accepted().build();
    }
}
