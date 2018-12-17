package com.pavel.jbsrm.company.service;

import com.pavel.jbsrm.common.exception.ResourceNotFoundException;
import com.pavel.jbsrm.common.utill.ObjectMapperUtills;
import com.pavel.jbsrm.company.Company;
import com.pavel.jbsrm.company.dto.CompanyDto;
import com.pavel.jbsrm.company.dto.CreateCompanyDto;
import com.pavel.jbsrm.company.dto.UpdateCompanyDto;
import com.pavel.jbsrm.company.repository.CompanyRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Transactional
    @Override
    public CompanyDto create(@Valid CreateCompanyDto createCompanyDto) {
        Company company = ObjectMapperUtills.mapTo(createCompanyDto, Company.class);
        return ObjectMapperUtills.mapTo(companyRepository.save(company), CompanyDto.class);
    }

    @Transactional
    @Override
    public CompanyDto update(long id, @Valid UpdateCompanyDto updateCompanyDto) throws ResourceNotFoundException {
        Optional<Company> company = Optional.of(companyRepository.getOne(id));
        company.ifPresent(cp -> ObjectMapperUtills.mapTo(updateCompanyDto, cp));

        return ObjectMapperUtills.mapTo(
                company.map(cp -> companyRepository.save(cp)).orElseThrow(ResourceNotFoundException::new), CompanyDto.class);
    }

    @Override
    public void updateDeleted(long id, boolean deleted) throws ResourceNotFoundException {
        Optional<Company> company = Optional.of(companyRepository.getOne(id));
        company.orElseThrow(ResourceNotFoundException::new)
                .setDeleted(deleted);
        companyRepository.save(company.get());
    }

    @Override
    @Transactional(readOnly = true)
    public CompanyDto find(long id) {
        return ObjectMapperUtills.mapTo(companyRepository.findById(id).orElse(Company.builder().build()), CompanyDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CompanyDto> findAllByPropsMatch(String searchParams) {
        List<CompanyDto> result = new ArrayList<>();
        if (!StringUtils.isBlank(searchParams)) {

            List<String> list = Arrays.stream(searchParams.trim().split(" "))
                    .filter(s -> !s.contains("")) //todo !s.equals("") check
                    .collect(Collectors.toList());

            companyRepository.findAllByPropsMatch(list)
                    .forEach(company -> result.add(ObjectMapperUtills.mapTo(company, CompanyDto.class)));
        }
        return result;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<CompanyDto> findAllPageByDeleted(boolean deleted, Pageable pageable) {
        return companyRepository.findByDeleted(deleted, pageable)
                .map(company -> ObjectMapperUtills.mapTo(company, CompanyDto.class));
    }
}
