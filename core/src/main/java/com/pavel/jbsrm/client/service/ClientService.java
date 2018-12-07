package com.pavel.jbsrm.client.service;

import com.pavel.jbsrm.client.dto.ClientDto;
import com.pavel.jbsrm.client.dto.CreateClientDto;
import com.pavel.jbsrm.client.dto.UpdateClientDto;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface ClientService {

    ClientDto createClient(@Valid CreateClientDto createClientDto);

    ClientDto updateClient(long id, @Valid UpdateClientDto updateClientDto);

    void deleteClient(long id);

    ClientDto find(long id);
}
