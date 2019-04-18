package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Arrays;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}

// Temp: for test data creation.
@Slf4j
@Component
class DataLoader implements ApplicationRunner {
    private CustomerRepository customerRepository;

    public DataLoader(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void run(ApplicationArguments args) {
        if (customerRepository.count() == 0) {
            log.info("Creating test data..................");
            customerRepository.saveAll(Arrays.asList(
                    Customer.of("sumo", "sumo", "river", 111L),
                    Customer.of("sumo1", "sumo1", "side", 112L)
            ));
        }
    }
}