package com.pavel.jbsrm.ttn;

import com.pavel.jbsrm.ttn.dto.CreateTtnDto;
import com.pavel.jbsrm.ttn.dto.TtnDto;
import com.pavel.jbsrm.ttn.dto.TtnQuickSearchDto;
import com.pavel.jbsrm.ttn.dto.UpdateTtnDto;
import com.pavel.jbsrm.ttn.service.TtnService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ttns")
public class TtnController {

    private TtnService ttnService;

    public TtnController(TtnService ttnService) {
        this.ttnService = ttnService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DISPATCHER') or hasRole('MANAGER')")
    public ResponseEntity<TtnDto> getById(@PathVariable long id) {
        return ttnService.find(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/quickSearch/{searchParams}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DISPATCHER') or hasRole('MANAGER')")
    public List<TtnQuickSearchDto> findAllByPropMatch(@PathVariable String searchParams) {
        return ttnService.findAllByPropsMatch(searchParams);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('DISPATCHER') or hasRole('MANAGER')")
    public Page<TtnDto> findAll(TtnFilter ttnFilter, Pageable pageable) {
        return ttnService.findAllPageByFilter(ttnFilter, pageable);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DISPATCHER') or hasRole('MANAGER')")
    public ResponseEntity<String> update(@PathVariable long id, @RequestBody UpdateTtnDto updateDto) {
        ttnService.update(id, updateDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('DISPATCHER') or hasRole('MANAGER')")
    public TtnDto create(@RequestBody CreateTtnDto createDto) {
        return ttnService.create(createDto);
    }

    @PutMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DISPATCHER') or hasRole('MANAGER')")
    public ResponseEntity<String> updateDeleted(@PathVariable long id, Boolean deleted) {
        ttnService.updateDeleted(id, deleted);
        return ResponseEntity.ok().build();
    }

}
