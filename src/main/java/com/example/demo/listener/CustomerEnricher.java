package com.example.demo.listener;

import com.example.demo.domain.Customer;
import com.example.demo.domain.CustomerDto;
import com.example.demo.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.binder.kafka.streams.annotations.KafkaStreamsProcessor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableBinding(KafkaStreamsProcessor.class)
public class CustomerEnricher {
    private final CustomerRepository customerRepository;

    public CustomerEnricher(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @StreamListener("input")
    @SendTo({"output"})
    public KStream<?, CustomerDto> process(KStream<?, CustomerDto> input) {

        // for Debug. Print in batches
        if (log.isDebugEnabled()) {
            input.print(Printed.toSysOut());
        }

        return input.map((key, val) -> {
            Customer customer = customerRepository.findById(val.getId()).orElse(null);
            if (customer != null && val.getCity() != null) {
                customer.setCity(val.getCity());
                customerRepository.save(customer);
                log.info("customer changed# " + customer.getCity());
                return new KeyValue<>(key, val);
            } else {
                log.info("customer not-changed# " + val.getCity());
                return new KeyValue<>(key, val);
            }
        });
    }

    @KafkaListener(id = "foo", topics = "customer-change-dlq")
    public void dlq(Message<?> in) {
        System.out.println("DLQ: " + in);
    }
}

//interface KStreamProcessorWithBranches {
//
//    @Input("input")
//    KStream<?, ?> input();
//
//    @Output("output1")
//    KStream<?, ?> output1();
//
//    @Output("output2")
//    KStream<?, ?> output2();
//
//    @Output("output3")
//    KStream<?, ?> output3();
//}