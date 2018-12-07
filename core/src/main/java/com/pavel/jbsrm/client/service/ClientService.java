package com.pavel.jbsrm.client.service;

import com.pavel.jbsrm.client.Client;
import com.pavel.jbsrm.client.dto.CreateClientDto;
import com.pavel.jbsrm.client.dto.UpdateClientDto;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface ClientService {

    Client createClient(@Valid CreateClientDto createClientDto);

    Client updateClient(long id, @Valid UpdateClientDto updateClientDto);

    void deleteClient(long id);
}
