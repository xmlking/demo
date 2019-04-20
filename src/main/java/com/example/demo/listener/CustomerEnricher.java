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

import java.util.Optional;

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
        input.print(Printed.toSysOut());

        input.map((key, val) -> {
            System.out.println(key);
            System.out.println(val);

            Optional<Customer> cust = customerRepository.findById(val.getId());
            if (cust.isPresent()) {
                Customer temp = cust.get();
                log.info("customer 1#  " + temp);
            } else {
                log.info("customer 2#  ");
            }

            return new KeyValue<>(key, val);
        });
        return input;
    }

    @KafkaListener(id = "foo", topics = "customer-change-dlq")
    public void dlq(Message<?> in) {
        System.out.println("DLQ: " + in);
    }
}


//Following source is used as a test producer.
//@EnableBinding(Source.class)
//static class TestSource {
//    private AtomicBoolean semaphore = new AtomicBoolean(true);
//
//    @Bean
//    @InboundChannelAdapter(channel = "test-source", poller = @Poller(fixedDelay = "${customer.poller.cron:1000}"))
//    public MessageSource<Long> sendTestData() {
//        return () ->
//                new GenericMessage<>(this.semaphore.getAndSet(!this.semaphore.get()) ? 2L : 3L);
//
//    }
//}

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