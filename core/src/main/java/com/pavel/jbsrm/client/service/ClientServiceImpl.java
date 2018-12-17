package com.pavel.jbsrm.client.service;

import com.pavel.jbsrm.client.Client;
import com.pavel.jbsrm.client.dto.ClientDto;
import com.pavel.jbsrm.client.dto.CreateClientDto;
import com.pavel.jbsrm.client.dto.UpdateClientDto;
import com.pavel.jbsrm.client.repository.ClientRepository;
import com.pavel.jbsrm.common.exception.ResourceNotFoundException;
import com.pavel.jbsrm.common.utill.ObjectMapperUtills;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Transactional
    @Override
    public ClientDto create(@Valid CreateClientDto createClientDto) {
        Client client = ObjectMapperUtills.mapTo(createClientDto, Client.class);
        return ObjectMapperUtills.mapTo(clientRepository.save(client), ClientDto.class);
    }

    @Transactional
    @Override
    public ClientDto update(long id, @Valid UpdateClientDto updateClientDto) throws ResourceNotFoundException {
        Optional<Client> client = Optional.of(clientRepository.getOne(id));
        client.ifPresent(cl -> ObjectMapperUtills.mapTo(updateClientDto, cl));

        return ObjectMapperUtills.mapTo(
                client.map(cl -> clientRepository.save(cl)).orElseThrow(ResourceNotFoundException::new), ClientDto.class);
    }

    @Override
    public void updateDeleted(long id, boolean deleted) throws ResourceNotFoundException {
        Optional<Client> client = Optional.of(clientRepository.getOne(id));
        client.orElseThrow(ResourceNotFoundException::new)
                .setDeleted(deleted);
        clientRepository.save(client.get());
    }

    @Override
    @Transactional(readOnly = true)
    public ClientDto find(long id) {
        return ObjectMapperUtills.mapTo(clientRepository.findById(id).orElse(Client.builder().build()), ClientDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ClientDto> findAllByPropsMatch(String searchParams) {
        List<ClientDto> result = new ArrayList<>();
        if (!StringUtils.isBlank(searchParams)) {

            List<String> list = Arrays.stream(searchParams.trim().split(" "))
                    .filter(s -> !s.contains("")) //todo !s.equals("") check
                    .collect(Collectors.toList());

            clientRepository.findAllByPropsMatch(list)
                    .forEach(client -> result.add(ObjectMapperUtills.mapTo(client, ClientDto.class)));
        }
        return result;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ClientDto> findAllPageByDeleted(boolean deleted, Pageable pageable) {
        return clientRepository.findByDeleted(deleted, pageable)
                .map(client -> ObjectMapperUtills.mapTo(client, ClientDto.class));
    }
}
