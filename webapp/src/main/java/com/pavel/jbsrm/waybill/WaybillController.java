package com.pavel.jbsrm.waybill;

import com.pavel.jbsrm.waybill.dto.CreateWaybillDto;
import com.pavel.jbsrm.waybill.dto.UpdateWaybillDto;
import com.pavel.jbsrm.waybill.dto.WaybillDto;
import com.pavel.jbsrm.waybill.service.WaybillService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import java.util.List;

@RestController
@RequestMapping("/api/waybills")
public class WaybillController {

    private WaybillService waybillService;

    public WaybillController(WaybillService waybillService) {
        this.waybillService = waybillService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<WaybillDto> getById(@PathVariable long id) {
        return waybillService.find(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/quickSearch/{searchParams}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public List<WaybillDto> findAllByPropMatch(@PathVariable String searchParams) {
        return waybillService.findAllByPropsMatch(searchParams);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public Page<WaybillDto> findAll(@Nullable @RequestParam Boolean deleted, Pageable pageable) {
        deleted = deleted == null ? false : deleted;
        return waybillService.findAllPageByFilter(deleted, pageable);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<String> update(@PathVariable long id, @RequestBody UpdateWaybillDto updateDto) {
        waybillService.update(id, updateDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public WaybillDto create(@RequestBody CreateWaybillDto createDto) {
        return waybillService.create(createDto);
    }

    @PutMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<String> updateDeleted(@PathVariable long id, Boolean deleted) {
        waybillService.updateDeleted(id, deleted);
        return ResponseEntity.ok().build();
    }
}
