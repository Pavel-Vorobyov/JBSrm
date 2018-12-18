package com.pavel.jbsrm.deed;

import com.pavel.jbsrm.deed.dto.CreateDeedDto;
import com.pavel.jbsrm.deed.dto.DeedDto;
import com.pavel.jbsrm.deed.dto.UpdateDeedDto;
import com.pavel.jbsrm.deed.service.DeedService;
import com.pavel.jbsrm.user.dto.CreateUserDto;
import com.pavel.jbsrm.user.dto.UpdateUserDto;
import com.pavel.jbsrm.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import java.util.List;

@RestController
@RequestMapping("/api/deeds")
public class DeedController {

    @Autowired
    private DeedService deedService;

    @GetMapping("/{id}")
    public DeedDto getById(@PathVariable long id) {
        return deedService.find(id);
    }

    @GetMapping("/quickSearch/{searchParams}")
    public List<DeedDto> findAllByPropMatch(@PathVariable String searchParams) {
        return deedService.findAllByPropsMatch(searchParams);
    }

    @GetMapping
    public Page<DeedDto> findAll(@Nullable @RequestParam Boolean deleted, Pageable pageable) {
        deleted = deleted == null ? false : deleted;
        return deedService.findAllPageByDeleted(deleted, pageable);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable long id, @RequestBody UpdateDeedDto updateDto) {
        deedService.update(id, updateDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public DeedDto create(@RequestBody CreateDeedDto createDto) {
        return deedService.create(createDto);
    }

    @PutMapping("/{id}/delete")
    public ResponseEntity<String> updateDeleted(@PathVariable long id, Boolean deleted) {
        deedService.updateDeleted(id, deleted);
        return ResponseEntity.ok().build();
    }

}
