package com.pavel.jbsrm.company.service;

import com.pavel.jbsrm.company.CompanyFilter;
import com.pavel.jbsrm.company.dto.CompanyDto;
import com.pavel.jbsrm.company.dto.CreateCompanyDto;
import com.pavel.jbsrm.company.dto.UpdateCompanyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Validated
public interface CompanyService {

    CompanyDto create(@Valid CreateCompanyDto createCompanyDto);

    Optional<CompanyDto> update(long id, @Valid UpdateCompanyDto updateCompanyDto);

    void updateDeleted(long id, boolean deleted);

    Optional<CompanyDto> find(long id);

    List<CompanyDto> findAllByPropsMatch(String searchParams);

    Page<CompanyDto> findAllPageByFilter(CompanyFilter filter, Pageable pageable);
}
