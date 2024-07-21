package com.nicmora.springbatchdemo.service;

import com.nicmora.springbatchdemo.entity.in.Customer;
import com.nicmora.springbatchdemo.entity.out.Client;
import com.nicmora.springbatchdemo.repository.out.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public void saveAll(List<Customer> customers) {
        List<Client> clients = customers.stream()
                .map(ctm -> {
                    Client client = new Client();
                    client.setFullname(ctm.getFirstname() + " " + ctm.getLastname());
                    return client;
                })
                .toList();

        clientRepository.saveAll(clients);
    }
}
