package com.pavel.jbsrm.company;

import com.pavel.jbsrm.company.dto.CompanyDto;
import com.pavel.jbsrm.company.dto.CreateCompanyDto;
import com.pavel.jbsrm.company.dto.UpdateCompanyDto;
import com.pavel.jbsrm.company.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/{id}")
    public CompanyDto getById(@PathVariable long id) {
        return companyService.find(id);
    }

    @GetMapping("/quickSearch/{searchParams}")
    public List<CompanyDto> findAllByPropMatch(@PathVariable String searchParams) {
        return companyService.findAllByPropsMatch(searchParams);
    }

    @GetMapping
    public Page<CompanyDto> findAll(@Nullable @RequestParam Boolean deleted, Pageable pageable) {
        deleted = deleted == null ? false : deleted;
        return companyService.findAllPageByDeleted(deleted, pageable);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable long id, @RequestBody UpdateCompanyDto updateDto) {
        companyService.update(id, updateDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public CompanyDto create(@RequestBody CreateCompanyDto createDto) {
        return companyService.create(createDto);
    }

    @PutMapping("/{id}/delete")
    public ResponseEntity<String> updateDeleted(@PathVariable long id, Boolean deleted) {
        companyService.updateDeleted(id, deleted);
        return ResponseEntity.ok().build();
    }
}
