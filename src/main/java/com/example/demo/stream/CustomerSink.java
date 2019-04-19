package com.example.demo.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface CustomerSink {

    String INPUT = "customer-input";

    @Input(INPUT)
    SubscribableChannel processMessage();
}