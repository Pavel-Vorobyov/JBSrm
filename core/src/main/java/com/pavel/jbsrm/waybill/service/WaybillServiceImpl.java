package com.pavel.jbsrm.waybill.service;

import com.pavel.jbsrm.common.exception.ResourceNotFoundException;
import com.pavel.jbsrm.common.utill.ObjectMapperUtills;
import com.pavel.jbsrm.waybill.Waybill;
import com.pavel.jbsrm.waybill.dto.CreateWaybillDto;
import com.pavel.jbsrm.waybill.dto.UpdateWaybillDto;
import com.pavel.jbsrm.waybill.dto.WaybillDto;
import com.pavel.jbsrm.waybill.repository.WaybillRepository;
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
public class WaybillServiceImpl implements WaybillService {

    @Autowired
    private WaybillRepository waybillRepository;

    @Transactional
    @Override
    public WaybillDto create(@Valid CreateWaybillDto createWaybillDto) {
        Waybill waybill = ObjectMapperUtills.mapTo(createWaybillDto, Waybill.class);
        return ObjectMapperUtills.mapTo(waybillRepository.save(waybill), WaybillDto.class);
    }

    @Transactional
    @Override
    public WaybillDto update(long id, @Valid UpdateWaybillDto updateWaybillDto) throws ResourceNotFoundException {
        Optional<Waybill> waybill = Optional.of(waybillRepository.getOne(id));
        waybill.ifPresent(cp -> ObjectMapperUtills.mapTo(updateWaybillDto, cp));

        return ObjectMapperUtills.mapTo(
                waybill.map(cp -> waybillRepository.save(cp)).orElseThrow(ResourceNotFoundException::new), WaybillDto.class);
    }

    @Override
    public void updateDeleted(long id, boolean deleted) throws ResourceNotFoundException {
        Optional<Waybill> waybill = Optional.of(waybillRepository.getOne(id));
        waybill.orElseThrow(ResourceNotFoundException::new)
                .setDeleted(deleted);
        waybillRepository.save(waybill.get());
    }

    @Override
    @Transactional(readOnly = true)
    public WaybillDto find(long id) {
        return ObjectMapperUtills.mapTo(waybillRepository.findById(id).orElse(Waybill.builder().build()), WaybillDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public List<WaybillDto> findAllByPropsMatch(String searchParams) {
        List<WaybillDto> result = new ArrayList<>();
        if (!StringUtils.isBlank(searchParams)) {

            List<String> list = Arrays.stream(searchParams.trim().split(" "))
                    .filter(s -> !s.contains("")) //todo !s.equals("") check
                    .collect(Collectors.toList());

            waybillRepository.findAllByPropsMatch(list)
                    .forEach(waybill -> result.add(ObjectMapperUtills.mapTo(waybill, WaybillDto.class)));
        }
        return result;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<WaybillDto> findAllPageByDeleted(boolean deleted, Pageable pageable) {
        return waybillRepository.findByDeleted(deleted, pageable)
                .map(waybill -> ObjectMapperUtills.mapTo(waybill, WaybillDto.class));
    }
}
