package com.pavel.jbsrm.client.service;

import com.pavel.jbsrm.client.Client;
import com.pavel.jbsrm.client.dto.ClientDto;
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
    public ClientDto createClient(@Valid CreateClientDto createClientDto) {
        Client client = ObjectMapperUtills.mapTo(createClientDto, Client.class);
        return ObjectMapperUtills.mapTo(clientRepository.save(client), ClientDto.class);
    }

    @Override
    public ClientDto updateClient(long id, @Valid UpdateClientDto updateClientDto) {
        Optional<Client> client = clientRepository.findById(id);
        client.ifPresent(cl -> ObjectMapperUtills.mapTo(updateClientDto, cl));

        return ObjectMapperUtills.mapTo(client.map(cl -> clientRepository.save(cl)).orElse(null), ClientDto.class);
    }

    @Override
    public void deleteClient(long id) {
        clientRepository.deleteById(id);
    }

    @Override
    public ClientDto find(long id) {
        return ObjectMapperUtills.mapTo(clientRepository.findById(id), ClientDto.class);
    }
}
