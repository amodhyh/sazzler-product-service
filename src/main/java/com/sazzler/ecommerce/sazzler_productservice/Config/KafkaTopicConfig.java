package com.sazzler.ecommerce.sazzler_productservice.Config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic productEventsTopic() {
        return TopicBuilder.name("sazzler-product-topic")
                .partitions(3) // 3 partitions allows up to 3 consumer instances to read in parallel
                .replicas(1)   // 1 replica because we only have a 1-node local cluster
                .build();
    }
}
