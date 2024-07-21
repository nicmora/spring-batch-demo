package com.nicmora.springbatchdemo.repository.in;

import com.nicmora.springbatchdemo.entity.in.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
