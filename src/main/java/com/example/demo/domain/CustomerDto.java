package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString()
@AllArgsConstructor(staticName = "of")
@Builder
public class CustomerDto {
    private Long id;
    private String city;
}