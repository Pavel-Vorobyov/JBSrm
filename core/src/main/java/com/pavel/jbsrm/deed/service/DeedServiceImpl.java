package com.pavel.jbsrm.deed.service;

import com.pavel.jbsrm.common.exception.ResourceNotFoundException;
import com.pavel.jbsrm.common.utill.ObjectMapperUtills;
import com.pavel.jbsrm.company.Company;
import com.pavel.jbsrm.company.dto.CompanyDto;
import com.pavel.jbsrm.company.dto.CreateCompanyDto;
import com.pavel.jbsrm.company.dto.UpdateCompanyDto;
import com.pavel.jbsrm.deed.Deed;
import com.pavel.jbsrm.deed.dto.CreateDeedDto;
import com.pavel.jbsrm.deed.dto.DeedDto;
import com.pavel.jbsrm.deed.dto.UpdateDeedDto;
import com.pavel.jbsrm.deed.repository.DeedRepository;
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
public class DeedServiceImpl implements DeedService {

    @Autowired
    private DeedRepository deedRepository;

    @Transactional
    @Override
    public DeedDto create(@Valid CreateDeedDto createDeedDto) {
        Deed deed = ObjectMapperUtills.mapTo(createDeedDto, Deed.class);
        return ObjectMapperUtills.mapTo(deedRepository.save(deed), DeedDto.class);
    }

    @Transactional
    @Override
    public DeedDto update(long id, @Valid UpdateDeedDto updateDeedDto) throws ResourceNotFoundException {
        Optional<Deed> deed = Optional.of(deedRepository.getOne(id));
        deed.ifPresent(d -> ObjectMapperUtills.mapTo(updateDeedDto, d));

        return ObjectMapperUtills.mapTo(
                deed.map(d -> deedRepository.save(d)).orElseThrow(ResourceNotFoundException::new), DeedDto.class);
    }

    @Override
    public void updateDeleted(long id, boolean deleted) throws ResourceNotFoundException {
        Optional<Deed> deed = Optional.of(deedRepository.getOne(id));
        deed.orElseThrow(ResourceNotFoundException::new)
                .setDeleted(deleted);
        deedRepository.save(deed.get());
    }

    @Override
    @Transactional(readOnly = true)
    public DeedDto find(long id) {
        return ObjectMapperUtills.mapTo(deedRepository.findById(id).orElse(Deed.builder().build()), DeedDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public List<DeedDto> findAllByPropsMatch(String searchParams) {
        List<DeedDto> result = new ArrayList<>();
        if (!StringUtils.isBlank(searchParams)) {

            List<String> list = Arrays.stream(searchParams.trim().split(" "))
                    .filter(s -> !s.contains("")) //todo !s.equals("") check
                    .collect(Collectors.toList());

            deedRepository.findAllByPropsMatch(list)
                    .forEach(deed -> result.add(ObjectMapperUtills.mapTo(deed, DeedDto.class)));
        }
        return result;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<DeedDto> findAllPageByDeleted(boolean deleted, Pageable pageable) {
        return deedRepository.findByDeleted(deleted, pageable)
                .map(deed -> ObjectMapperUtills.mapTo(deed, DeedDto.class));
    }
}
