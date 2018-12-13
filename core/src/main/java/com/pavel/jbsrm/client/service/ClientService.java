package com.pavel.jbsrm.client.service;

import com.pavel.jbsrm.client.Client;
import com.pavel.jbsrm.client.dto.ClientDto;
import com.pavel.jbsrm.client.dto.CreateClientDto;
import com.pavel.jbsrm.client.dto.UpdateClientDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface ClientService {

    ClientDto createClient(@Valid CreateClientDto createClientDto);

    ClientDto updateClient(long id, @Valid UpdateClientDto updateClientDto);

    void deleteClient(long id);

    void restoreClient(long id);

    ClientDto find(long id);

    List<ClientDto> findAllByPropsMatch(String searchParams);

    Page<ClientDto> findAllPageByActive(boolean isActive, Pageable pageable);
}
