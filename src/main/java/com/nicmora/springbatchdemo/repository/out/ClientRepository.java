package com.nicmora.springbatchdemo.repository.out;

import com.nicmora.springbatchdemo.entity.out.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

}
