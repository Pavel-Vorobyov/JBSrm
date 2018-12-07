package com.pavel.jbsrm.client.service;

import com.pavel.jbsrm.client.Client;
import com.pavel.jbsrm.client.dto.CreateClientDto;
import com.pavel.jbsrm.client.dto.UpdateClientDto;
import com.pavel.jbsrm.client.repository.ClientRepository;
import com.pavel.jbsrm.common.utill.ObjectMapperUtills;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Client createClient(@Valid CreateClientDto createClientDto) {
        Client client = ObjectMapperUtills.mapTo(createClientDto, Client.class);
        return clientRepository.save(client);
    }

    @Override
    public Client updateClient(long id, @Valid UpdateClientDto updateClientDto) {
        Optional<Client> client = clientRepository.findById(id);
        client.ifPresent(cl -> ObjectMapperUtills.mapTo(cl, updateClientDto));

        return client.map(cl -> clientRepository.save(cl)).orElse(null);
    }

    @Override
    public void deleteClient(long id) {
        clientRepository.deleteById(id);
    }
}
