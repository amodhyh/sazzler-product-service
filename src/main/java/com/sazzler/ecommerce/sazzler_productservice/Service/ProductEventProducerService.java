package com.sazzler.ecommerce.sazzler_productservice.Service;

import com.sazzler.ecommerce.api_def.product_service.DTO.ProductEvent;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class ProductEventProducerService {

//   the template is used to send messages to kafka topic
    private final KafkaTemplate<String, ProductEvent> kafkaTemplate;
    //the topic name is used to specify the topic to which the message will be sent
    private  String TOPIC_NAME;

     public ProductEventProducerService(@Value("${spring.kafka.topic.name}") String topicName, KafkaTemplate<String, ProductEvent> kafkaTemplate) {
         this.kafkaTemplate = kafkaTemplate;
         this.TOPIC_NAME=topicName;

    }

    public void sendMessage(String key,ProductEvent productEvent) {
        //Async with Callbacks
//        can process many other tasks or requests while the message is being sent in the background.High Throughput
        //non-blocking threads
        CompletableFuture<SendResult<String,ProductEvent>> kafkFuture = kafkaTemplate.send(TOPIC_NAME,key, productEvent);
        //when complete -> non-blocking fast callbacks
        //when complete async -> blocking slow callbacks with heacy computation and I/O operations.It assigns a separate
        // thread pool for executing the callbacks.
        kafkFuture.whenComplete(
                (result,except)->{
                    if(except==null){
                        //{} is a placeholder for variable substitution
                       log.info(" Message sent successfully. Topic: {}, Key: {}, Partition: {}, Offset: {}",
                            result.getRecordMetadata().topic(),
                            key,
                            result.getRecordMetadata().partition(),
                            result.getRecordMetadata().offset());
                    }else{
                        log.error("Error sending message to topic:{}.key : {}, message : {}, exception : {} ",
                                TOPIC_NAME,
                                key,
                                productEvent.toString(),
                                except.getMessage());
                    }
                }
        );
     }
        // flush forces all buffered messages to be sent synchronously before exiting.
    //marks a method that should be executed exactly once just before the Spring container destroys the bean
    @PreDestroy
    public void shutdown() {
        kafkaTemplate.flush();
        log.info("KafkaTemplate flushed on shutdown");
    }
}
    /**
     * When the application receives a shutdown signal, @PreDestroy ensures that the shutdown()
     * method is called, which in turn uses kafkaTemplate.flush() to synchronously drain all unsent messages
     * from the buffer, guaranteeing that all data is delivered to Kafka before the service terminates.
     * **/


