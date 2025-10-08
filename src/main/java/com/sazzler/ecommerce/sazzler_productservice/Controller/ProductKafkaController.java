package com.sazzler.ecommerce.sazzler_productservice.Controller;

import com.sazzler.ecommerce.sazzler_productservice.Service.MessageProducerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductKafkaController {
    private final MessageProducerService messageProducerService;

    public ProductKafkaController(MessageProducerService messageProducerService) {
        this.messageProducerService = messageProducerService;
    }

    @PostMapping("/kafka/send")
    public void sendMessage(String key, String message) {
        messageProducerService.sendMessage(key, message);
    }

}
