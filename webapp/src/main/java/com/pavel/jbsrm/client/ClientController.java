package com.pavel.jbsrm.client;

import com.pavel.jbsrm.client.dto.ClientDto;
import com.pavel.jbsrm.client.dto.CreateClientDto;
import com.pavel.jbsrm.client.dto.UpdateClientDto;
import com.pavel.jbsrm.client.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/{id}")
    public ClientDto getClientById(@PathVariable long id) {
        return clientService.find(id);
    }

    @GetMapping("/quickSearch/{searchParams}")
    public List<ClientDto> findAllByPropMatch(@PathVariable String searchParams) {
        return clientService.findAllByPropsMatch(searchParams);
    }

    @GetMapping
    public Page<ClientDto> findAllClients(@Nullable @RequestParam Boolean deleted, Pageable pageable) {
        deleted = deleted == null || deleted;
        return clientService.findAllPageByDeleted(deleted, pageable);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable long id, @RequestBody UpdateClientDto updateDto) {
        clientService.update(id, updateDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ClientDto create(@RequestBody CreateClientDto createClientDto) {
        return clientService.create(createClientDto);
    }

    @PutMapping("/{id}/delete")
    public ResponseEntity<String> updateDelete(@PathVariable long id, Boolean deleted) {
        clientService.updateDeleted(id, deleted);
        return ResponseEntity.ok().build();
    }
}
