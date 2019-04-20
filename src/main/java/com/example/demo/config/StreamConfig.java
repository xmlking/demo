//package com.example.demo.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.stream.annotation.EnableBinding;
//import org.springframework.cloud.stream.schema.client.ConfluentSchemaRegistryClient;
//import org.springframework.cloud.stream.schema.client.SchemaRegistryClient;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class StreamConfig {
//
//    @Bean
//    public SchemaRegistryClient schemaRegistryClient(
//            @Value("${spring.cloud.stream.kafka.streams.binder.configuration.schema.registry.url}") String endpoint
//    ) {
//        ConfluentSchemaRegistryClient confluentSchemaRegistryClient = new ConfluentSchemaRegistryClient();
//        confluentSchemaRegistryClient.setEndpoint(endpoint);
//        return confluentSchemaRegistryClient;
//    }
//}