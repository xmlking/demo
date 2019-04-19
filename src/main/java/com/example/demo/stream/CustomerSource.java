package com.example.demo.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface CustomerSource {

    String OUTPUT = "customer-output";

    @Output(OUTPUT)
    MessageChannel created();
}