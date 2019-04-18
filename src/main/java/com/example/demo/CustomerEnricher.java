package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.GenericMessage;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@EnableBinding(Processor.class)
public class CustomerEnricher {
    private final CustomerRepository customerRepository;

    public CustomerEnricher(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @ServiceActivator(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
    public Long transform(Long payload) {
        Optional<Customer>  cust = customerRepository.findById(payload);
        if(cust.isPresent()){
            Customer temp =  cust.get();
            log.info("customer 2#  " + temp);
        } else {
            return payload;
        }
        return payload;
    }

    //Following source is used as a test producer.
    @EnableBinding(Source.class)
    static class TestSource {

        private AtomicBoolean semaphore = new AtomicBoolean(true);

        @Bean
        @InboundChannelAdapter(channel = "test-source", poller = @Poller(fixedDelay = "${customer.poller.cron:1000}"))
        public MessageSource<Long> sendTestData() {
            return () ->
                    new GenericMessage<>(this.semaphore.getAndSet(!this.semaphore.get()) ? 2L : 3L);

        }
    }

    //Following sink is used as a test consumer.
    @EnableBinding(Sink.class)
    static class TestSink {

        @StreamListener("test-sink")
        public void receive(String payload) {
            log.info("Data received: " + payload);
        }
    }

    public interface Sink {
        @Input("test-sink")
        SubscribableChannel sampleSink();
    }

    public interface Source {
        @Output("test-source")
        MessageChannel sampleSource();
    }
}