package com.pavel.jbsrm.client;

import com.pavel.jbsrm.client.dto.ClientDto;
import com.pavel.jbsrm.client.dto.CreateClientDto;
import com.pavel.jbsrm.client.dto.UpdateClientDto;
import com.pavel.jbsrm.client.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.querydsl.QSort;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
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
    public Page<ClientDto> findAllClients(
            @Nullable @RequestParam Integer page,
            @Nullable @RequestParam Integer rowsPerPage,
            @Nullable @RequestParam Boolean isDeleted) {
        page = page == null ? 0 : page;
        rowsPerPage = rowsPerPage == null ? 5 : rowsPerPage;
        isDeleted = isDeleted == null ? false : isDeleted;

        QSort qSort = new QSort();
        Pageable pageable = new QPageRequest(page, rowsPerPage, qSort);
        return clientService.findAllPageByDeleted(isDeleted, pageable);
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> updateClient(@PathVariable long id, @RequestBody UpdateClientDto updateDto) {//todo
        clientService.updateClient(id, updateDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/new-client")
    public ResponseEntity<ClientDto> createClient(@RequestBody CreateClientDto createClientDto) {
        ClientDto clientDto = clientService.createClient(createClientDto);
        return ResponseEntity.ok().body(clientDto);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable long id) {
        clientService.deleteClient(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/restore/{id}")
    public ResponseEntity<String> restoreClient(@PathVariable long id) {
        clientService.restoreClient(id);
        return ResponseEntity.ok().build();
    }
}
