package com.pavel.jbsrm.transport;

import com.pavel.jbsrm.transport.dto.CreateTransportDto;
import com.pavel.jbsrm.transport.dto.TransportDto;
import com.pavel.jbsrm.transport.dto.UpdateTransportDto;
import com.pavel.jbsrm.transport.service.TransportService;
import com.pavel.jbsrm.user.dto.CreateUserDto;
import com.pavel.jbsrm.user.dto.UpdateUserDto;
import com.pavel.jbsrm.user.dto.UserDto;
import com.pavel.jbsrm.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import java.util.List;

@RestController
@RequestMapping("/api/transports")
public class TransportController {

    @Autowired
    private TransportService userService;

    @GetMapping("/{id}")
    public TransportDto getById(@PathVariable long id) {
        return userService.find(id);
    }

    @GetMapping("/quickSearch/{searchParams}")
    public List<TransportDto> findAllByPropMatch(@PathVariable String searchParams) {
        return userService.findAllByPropsMatch(searchParams);
    }

    @GetMapping
    public Page<TransportDto> findAll(@Nullable @RequestParam Boolean deleted, Pageable pageable) {
        deleted = deleted == null ? false : deleted;
        return userService.findAllPageByDeleted(deleted, pageable);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable long id, @RequestBody UpdateTransportDto updateDto) {
        userService.update(id, updateDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public TransportDto create(@RequestBody CreateTransportDto createDto) {
        return userService.create(createDto);
    }

    @PutMapping("/{id}/delete")
    public ResponseEntity<String> updateDeleted(@PathVariable long id, Boolean deleted) {
        userService.updateDeleted(id, deleted);
        return ResponseEntity.ok().build();
    }

}
