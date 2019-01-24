package com.pavel.jbsrm.company;

import com.pavel.jbsrm.company.dto.CompanyDto;
import com.pavel.jbsrm.company.dto.CreateCompanyDto;
import com.pavel.jbsrm.company.dto.UpdateCompanyDto;
import com.pavel.jbsrm.company.service.CompanyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> getById(@PathVariable long id) {
        return companyService.find(id)
                .map(ResponseEntity.ok()::body)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/quickSearch/{searchParams}")
    public List<CompanyDto> findAllByPropMatch(@PathVariable String searchParams) {
        return companyService.findAllByPropsMatch(searchParams);
    }

    @GetMapping
    public Page<CompanyDto> findAll(CompanyFilter filter, Pageable pageable) {
        return companyService.findAllPageByFilter(filter, pageable);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable long id, @RequestBody UpdateCompanyDto updateDto) {
        companyService.update(id, updateDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<CompanyDto> create(@RequestBody CreateCompanyDto createDto) {
        ResponseEntity<CompanyDto> companyDto;
        try {
            companyDto = ResponseEntity.ok().body(companyService.create(createDto));
        } catch (EntityExistsException e) {
            companyDto = ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (RuntimeException e) {
            companyDto = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return companyDto;
    }

    @PutMapping("/{id}/delete")
    public ResponseEntity<String> delete(@PathVariable long id) {
        companyService.updateDeleted(id, true);
        return ResponseEntity.ok().build();
    }
}
