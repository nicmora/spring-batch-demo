package com.nicmora.springbatchdemo.service;

import com.nicmora.springbatchdemo.entity.in.Customer;
import com.nicmora.springbatchdemo.repository.in.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

}
