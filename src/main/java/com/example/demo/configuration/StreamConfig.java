package com.example.demo.configuration;


import com.example.demo.stream.CustomerSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.schema.client.ConfluentSchemaRegistryClient;
import org.springframework.cloud.stream.schema.client.SchemaRegistryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding(CustomerSource.class)
public class StreamConfig {

    @Bean
    public SchemaRegistryClient schemaRegistryClient(@Value("${spring.cloud.stream.schema-registry-client.endpoint}") String endpoint) {
        ConfluentSchemaRegistryClient confluentSchemaRegistryClient = new ConfluentSchemaRegistryClient();
        confluentSchemaRegistryClient.setEndpoint(endpoint);
        return confluentSchemaRegistryClient;
    }
}