package com.pavel.jbsrm.transport;

import com.pavel.jbsrm.transport.dto.CreateTransportDto;
import com.pavel.jbsrm.transport.dto.TransportDto;
import com.pavel.jbsrm.transport.dto.UpdateTransportDto;
import com.pavel.jbsrm.transport.service.TransportService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import java.util.List;

@RestController
@RequestMapping("/api/transports")
public class TransportController {

    private TransportService userService;

    public TransportController(TransportService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DISPATCHER')")
    public ResponseEntity<TransportDto> getById(@PathVariable long id) {
        return userService.find(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/quickSearch/{searchParams}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DISPATCHER')")
    public List<TransportDto> findAllByPropMatch(@PathVariable String searchParams) {
        return userService.findAllByPropsMatch(searchParams);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<TransportDto> findAll(@Nullable @RequestParam Boolean deleted, Pageable pageable) {
        deleted = deleted == null ? false : deleted;
        return userService.findAllPageByFilter(deleted, pageable);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> update(@PathVariable long id, @RequestBody UpdateTransportDto updateDto) {
        userService.update(id, updateDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public TransportDto create(@RequestBody CreateTransportDto createDto) {
        return userService.create(createDto);
    }

    @PutMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateDeleted(@PathVariable long id, Boolean deleted) {
        userService.updateDeleted(id, deleted);
        return ResponseEntity.ok().build();
    }

}
