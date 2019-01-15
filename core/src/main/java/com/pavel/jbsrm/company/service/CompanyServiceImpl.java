package com.pavel.jbsrm.company.service;

import com.pavel.jbsrm.common.utill.ObjectMapperUtills;
import com.pavel.jbsrm.common.utill.StringConverter;
import com.pavel.jbsrm.company.Company;
import com.pavel.jbsrm.company.dto.CompanyDto;
import com.pavel.jbsrm.company.dto.CreateCompanyDto;
import com.pavel.jbsrm.company.dto.UpdateCompanyDto;
import com.pavel.jbsrm.company.repository.CompanyRepository;
import com.pavel.jbsrm.user.User;
import com.pavel.jbsrm.user.UserRole;
import com.pavel.jbsrm.user.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    private CompanyRepository companyRepository;
    private UserRepository userRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository, UserRepository userRepository) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public CompanyDto create(@Valid CreateCompanyDto createCompanyDto) {
        if (userRepository.findByEmail(createCompanyDto.getSystemAdminEmail()).isPresent()) {
            throw new EntityExistsException("User with such email is already present!");
        }

        Company company = companyRepository.save(ObjectMapperUtills.mapTo(createCompanyDto, Company.class));

        User systemAdminToSave = new User();
        systemAdminToSave.setCompany(company);
        systemAdminToSave.setEmail(createCompanyDto.getSystemAdminEmail());
        systemAdminToSave.setPassword(StringConverter.getHash(createCompanyDto.getSystemAdminPassword()));
        systemAdminToSave.setUserRole(UserRole.ROLE_SYSTEM_ADMIN);
        userRepository.save(systemAdminToSave);

        CompanyDto companyDto = ObjectMapperUtills.mapTo(company, CompanyDto.class);
        companyDto.setSystemAdminEmail(systemAdminToSave.getEmail());
        return companyDto;
    }

    @Transactional
    @Override
    public Optional<CompanyDto> update(long id, @Valid UpdateCompanyDto updateCompanyDto) {
        Company company = companyRepository.getOne(id);
        ObjectMapperUtills.mapTo(updateCompanyDto, company);
        company.setId(id);

        return Optional.of(ObjectMapperUtills.mapTo(companyRepository.save(company), CompanyDto.class));
    }

    @Override
    public void updateDeleted(long id, boolean deleted) {
        Company company = companyRepository.getOne(id);
        company.setDeleted(deleted);
        companyRepository.save(company);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CompanyDto> find(long id) {
        Optional<CompanyDto> companyDto =
                Optional.of(ObjectMapperUtills.mapTo(companyRepository.findById(id).orElse(Company.builder().build()), CompanyDto.class));

        companyDto.ifPresent(companyDto1 ->
            userRepository.findByCompanyIdAndUserRole(id, UserRole.ROLE_SYSTEM_ADMIN)
                    .ifPresent(user -> companyDto1.setSystemAdminEmail(user.getEmail())));

        return companyDto;
    }

    @Transactional(readOnly = true)
    @Override
    public List<CompanyDto> findAllByPropsMatch(String searchParams) {
        List<CompanyDto> result = new ArrayList<>();
        if (!StringUtils.isBlank(searchParams)) {

            List<String> list = Arrays.stream(searchParams.trim().split(" "))
                    .filter(s -> !s.equals(""))
                    .collect(Collectors.toList());

            companyRepository.findAllByPropsMatch(list)
                    .forEach(company -> result.add(ObjectMapperUtills.mapTo(company, CompanyDto.class)));
        }
        return result;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<CompanyDto> findAllPageByFilter(boolean deleted, Pageable pageable) {
        return companyRepository.findByDeleted(deleted, pageable)
                .map(company -> ObjectMapperUtills.mapTo(company, CompanyDto.class));
    }
}
