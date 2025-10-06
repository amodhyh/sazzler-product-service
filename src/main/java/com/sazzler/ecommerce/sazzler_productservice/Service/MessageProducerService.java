package com.sazzler.ecommerce.sazzler_productservice.Service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MessageProducerService {

//    the template is used to send messages to kafka topic
    private final KafkaTemplate<String, String> kafkaTemplate;
    //the topic name is used to specify the topic to which the message will be sent
    @Value("${kafka.topic.name}")
    private final String TOPIC_NAME;

    public void sendMessage(String key,String message) {

        kafkaTemplate.send(TOPIC_NAME,key, message);
        System.out.println("Producer sent message: '" + message + "' with key '" + key + "' to topic: " + TOPIC_NAME);

    }
}
