package com.pavel.jbsrm.client.service;

import com.pavel.jbsrm.client.dto.ClientDto;
import com.pavel.jbsrm.client.dto.CreateClientDto;
import com.pavel.jbsrm.client.dto.UpdateClientDto;
import com.pavel.jbsrm.common.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface ClientService {

    ClientDto create(@Valid CreateClientDto createClientDto);

    ClientDto update(long id, @Valid UpdateClientDto updateClientDto) throws ResourceNotFoundException;

    void updateDeleted(long id, boolean deleted) throws ResourceNotFoundException;

    ClientDto find(long id);

    List<ClientDto> findAllByPropsMatch(String searchParams);

    Page<ClientDto> findAllPageByDeleted(boolean deleted, Pageable pageable);
}
