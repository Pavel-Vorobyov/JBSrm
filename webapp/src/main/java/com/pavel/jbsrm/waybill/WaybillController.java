package com.pavel.jbsrm.waybill;

import com.pavel.jbsrm.waybill.dto.CreateWaybillDto;
import com.pavel.jbsrm.waybill.dto.UpdateWaybillDto;
import com.pavel.jbsrm.waybill.dto.WaybillDto;
import com.pavel.jbsrm.waybill.service.WaybillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import java.util.List;

@RestController
@RequestMapping("/api/waybills")
public class WaybillController {

    @Autowired
    private WaybillService waybillService;

    @GetMapping("/{id}")
    public WaybillDto getById(@PathVariable long id) {
        return waybillService.find(id);
    }

    @GetMapping("/quickSearch/{searchParams}")
    public List<WaybillDto> findAllByPropMatch(@PathVariable String searchParams) {
        return waybillService.findAllByPropsMatch(searchParams);
    }

    @GetMapping
    public Page<WaybillDto> findAll(@Nullable @RequestParam Boolean deleted, Pageable pageable) {
        deleted = deleted == null ? false : deleted;
        return waybillService.findAllPageByDeleted(deleted, pageable);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable long id, @RequestBody UpdateWaybillDto updateDto) {
        waybillService.update(id, updateDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public WaybillDto create(@RequestBody CreateWaybillDto createDto) {
        return waybillService.create(createDto);
    }

    @PutMapping("/{id}/delete")
    public ResponseEntity<String> updateDeleted(@PathVariable long id, Boolean deleted) {
        waybillService.updateDeleted(id, deleted);
        return ResponseEntity.ok().build();
    }

}
