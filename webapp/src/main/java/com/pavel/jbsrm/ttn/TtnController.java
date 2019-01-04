package com.pavel.jbsrm.ttn;

import com.pavel.jbsrm.ttn.dto.CreateTtnDto;
import com.pavel.jbsrm.ttn.dto.TtnDto;
import com.pavel.jbsrm.ttn.dto.TtnQuickSearchDto;
import com.pavel.jbsrm.ttn.dto.UpdateTtnDto;
import com.pavel.jbsrm.ttn.service.TtnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ttns")
public class TtnController {

    @Autowired
    private TtnService ttnService;

//    @RequiresPermissions("ttns:read")
//    @RequiresRoles("admin")
    @GetMapping("/{id}")
    public TtnDto getById(@PathVariable long id) {
        return ttnService.find(id);
    }

    //    @RequiresPermissions("ttns:read")
    @GetMapping("/quickSearch/{searchParams}")
    public List<TtnQuickSearchDto> findAllByPropMatch(@PathVariable String searchParams) {
        return ttnService.findAllByPropsMatch(searchParams);
    }

    //    @RequiresPermissions("ttns:read")
    @GetMapping
    public Page<TtnDto> findAll(TtnFilter ttnFilter, Pageable pageable) {
        return ttnService.findAllPageByDeleted(ttnFilter, pageable);
    }

    //    @RequiresPermissions("ttns:update")
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable long id, @RequestBody UpdateTtnDto updateDto) {
        ttnService.update(id, updateDto);
        return ResponseEntity.ok().build();
    }

    //    @RequiresPermissions("ttns:create")
    @PostMapping
    public TtnDto create(@RequestBody CreateTtnDto createDto) {
        return ttnService.create(createDto);
    }

    //    @RequiresPermissions("ttns:delete")
    @PutMapping("/{id}/delete")
    public ResponseEntity<String> updateDeleted(@PathVariable long id, Boolean deleted) {
        ttnService.updateDeleted(id, deleted);
        return ResponseEntity.ok().build();
    }

}
