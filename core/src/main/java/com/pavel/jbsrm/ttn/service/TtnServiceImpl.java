package com.pavel.jbsrm.ttn.service;

import com.pavel.jbsrm.common.exception.ResourceNotFoundException;
import com.pavel.jbsrm.common.utill.ObjectMapperUtills;
import com.pavel.jbsrm.company.Company;
import com.pavel.jbsrm.company.dto.CompanyDto;
import com.pavel.jbsrm.company.dto.CreateCompanyDto;
import com.pavel.jbsrm.company.dto.UpdateCompanyDto;
import com.pavel.jbsrm.ttn.Ttn;
import com.pavel.jbsrm.ttn.dto.CreateTtnDto;
import com.pavel.jbsrm.ttn.dto.TtnDto;
import com.pavel.jbsrm.ttn.dto.UpdateTtnDto;
import com.pavel.jbsrm.ttn.repository.TtnRepository;
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
public class TtnServiceImpl implements TtnService {

    @Autowired
    private TtnRepository ttnRepository;

    @Transactional
    @Override
    public TtnDto create(@Valid CreateTtnDto createTtnDto) {
        Ttn ttn = ObjectMapperUtills.mapTo(createTtnDto, Ttn.class);
        return ObjectMapperUtills.mapTo(ttnRepository.save(ttn), TtnDto.class);
    }

    @Transactional
    @Override
    public TtnDto update(long id, @Valid UpdateTtnDto updateTtnDto) throws ResourceNotFoundException {
        Optional<Ttn> ttn = Optional.of(ttnRepository.getOne(id));
        ttn.ifPresent(t -> ObjectMapperUtills.mapTo(updateTtnDto, t));

        return ObjectMapperUtills.mapTo(
                ttn.map(t -> ttnRepository.save(t)).orElseThrow(ResourceNotFoundException::new), TtnDto.class);
    }

    @Override
    public void updateDeleted(long id, boolean deleted) throws ResourceNotFoundException {
        Optional<Ttn> ttn = Optional.of(ttnRepository.getOne(id));
        ttn.orElseThrow(ResourceNotFoundException::new)
                .setDeleted(deleted);
        ttnRepository.save(ttn.get());
    }

    @Override
    @Transactional(readOnly = true)
    public TtnDto find(long id) {
        return ObjectMapperUtills.mapTo(ttnRepository.findById(id).orElse(Ttn.builder().build()), TtnDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public List<TtnDto> findAllByPropsMatch(String searchParams) {
        List<TtnDto> result = new ArrayList<>();
        if (!StringUtils.isBlank(searchParams)) {

            List<String> list = Arrays.stream(searchParams.trim().split(" "))
                    .filter(s -> !s.contains("")) //todo !s.equals("") check
                    .collect(Collectors.toList());

            ttnRepository.findAllByPropsMatch(list)
                    .forEach(ttn -> result.add(ObjectMapperUtills.mapTo(ttn, TtnDto.class)));
        }
        return result;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<TtnDto> findAllPageByDeleted(boolean deleted, Pageable pageable) {
        return ttnRepository.findByDeleted(deleted, pageable)
                .map(ttn -> ObjectMapperUtills.mapTo(ttn, TtnDto.class));
    }
}
