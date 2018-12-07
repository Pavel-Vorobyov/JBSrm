package com.pavel.jbsrm.client;

import com.pavel.jbsrm.client.dto.ClientDto;
import com.pavel.jbsrm.client.dto.UpdateClientDto;
import com.pavel.jbsrm.client.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/{id}")
    public ClientDto getClientById(@PathVariable long id) {
        return clientService.find(id);
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> updateClient(@PathVariable long id, @RequestBody UpdateClientDto updateDto) {//todo
        clientService.updateClient(id, updateDto);
        return ResponseEntity.ok().build();
    }

}
