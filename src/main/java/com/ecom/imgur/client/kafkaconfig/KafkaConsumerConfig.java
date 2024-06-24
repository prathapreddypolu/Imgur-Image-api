package com.ecom.imgur.client.kafkaconfig;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import org.springframework.kafka.listener.ContainerProperties.AckMode;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    /**
     * Creates a Kafka ConsumerFactory for consuming messages.
     *
     * @return The configured ConsumerFactory.
     */
    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9093");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "batch-id");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "50");
        return new DefaultKafkaConsumerFactory<>(props);
    }
    /**
     * Creates a ConcurrentKafkaListenerContainerFactory for Kafka message listeners.
     *
     * @return The configured ConcurrentKafkaListenerContainerFactory.
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.getContainerProperties().setAckMode(AckMode.MANUAL);
        factory.setBatchListener(true);
        return factory;
    }
}