package com.pavel.jbsrm.company.service;

import com.pavel.jbsrm.common.exception.ResourceNotFoundException;
import com.pavel.jbsrm.company.dto.CompanyDto;
import com.pavel.jbsrm.company.dto.CreateCompanyDto;
import com.pavel.jbsrm.company.dto.UpdateCompanyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface CompanyService {

    CompanyDto create(@Valid CreateCompanyDto createCompanyDto);

    CompanyDto update(long id, @Valid UpdateCompanyDto updateCompanyDto) throws ResourceNotFoundException;

    void updateDeleted(long id, boolean deleted) throws ResourceNotFoundException;

    CompanyDto find(long id);

    List<CompanyDto> findAllByPropsMatch(String searchParams);

    Page<CompanyDto> findAllPageByDeleted(boolean deleted, Pageable pageable);
}
