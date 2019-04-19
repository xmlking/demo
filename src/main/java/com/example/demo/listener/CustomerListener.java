package com.example.demo.listener;

import com.example.demo.domain.CustomerDto;
import com.example.demo.stream.CustomerSink;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(CustomerSink.class)
public class CustomerListener {

    @StreamListener(CustomerSink.INPUT)
    public void process(CustomerDto customer) {

        System.out.println(customer);
    }
}