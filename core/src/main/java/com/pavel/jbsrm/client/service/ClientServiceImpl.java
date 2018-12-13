package com.pavel.jbsrm.client.service;

import com.pavel.jbsrm.client.Client;
import com.pavel.jbsrm.client.dto.ClientDto;
import com.pavel.jbsrm.client.dto.CreateClientDto;
import com.pavel.jbsrm.client.dto.UpdateClientDto;
import com.pavel.jbsrm.client.repository.ClientRepository;
import com.pavel.jbsrm.common.utill.ObjectMapperUtills;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public ClientDto createClient(@Valid CreateClientDto createClientDto) {
        Client client = ObjectMapperUtills.mapTo(createClientDto, Client.class);
        client.setActive(true);
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
        updateActiveStatus(id, false);
    }

    @Override
    public void restoreClient(long id) {
        updateActiveStatus(id, true);
    }

    private void updateActiveStatus(long id, boolean isActive) {
        Optional<Client> client = clientRepository.findById(id);
        client.ifPresent(c -> {
            c.setActive(isActive);
            clientRepository.save(client.get());
        });
    }

    @Override
    public ClientDto find(long id) {
        return ObjectMapperUtills.mapTo(clientRepository.findById(id).orElse(Client.builder().build()), ClientDto.class);
    }

    @Override
    public List<ClientDto> findAllByPropsMatch(String searchParams) {
        List<ClientDto> result = new ArrayList<>();
        if (!StringUtils.isBlank(searchParams)) {

            String[] parsedSearchParams = searchParams.trim().split(" ");
            List<String> list = new ArrayList<>(Arrays.asList(parsedSearchParams));
            list.removeAll(Arrays.asList("", null));

            clientRepository.findAllByPropsMatch(list)
                    .forEach(c -> result.add(ObjectMapperUtills.mapTo(c, ClientDto.class)));
        }
        return result;
    }

    @Override
    public Page<ClientDto> findAllPageByActive(boolean isActive, Pageable pageable) {
        return clientRepository.findByIsActive(isActive, pageable).map(client -> ObjectMapperUtills.mapTo(client, ClientDto.class));
    }
}
