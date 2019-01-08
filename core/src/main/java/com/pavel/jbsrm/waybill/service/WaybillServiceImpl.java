package com.pavel.jbsrm.waybill.service;

import com.pavel.jbsrm.common.utill.ObjectMapperUtills;
import com.pavel.jbsrm.ttn.TtnState;
import com.pavel.jbsrm.ttn.repository.TtnRepository;
import com.pavel.jbsrm.waybill.CheckPointStatus;
import com.pavel.jbsrm.waybill.Waybill;
import com.pavel.jbsrm.waybill.dto.CreateWaybillDto;
import com.pavel.jbsrm.waybill.dto.UpdateWaybillDto;
import com.pavel.jbsrm.waybill.dto.WaybillDto;
import com.pavel.jbsrm.waybill.repository.CheckPointRepository;
import com.pavel.jbsrm.waybill.repository.WaybillRepository;
import org.apache.commons.lang3.StringUtils;
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

    private WaybillRepository waybillRepository;
    private CheckPointRepository checkPointRepository;
    private TtnRepository ttnRepository;

    public WaybillServiceImpl(WaybillRepository waybillRepository, CheckPointRepository checkPointRepository, TtnRepository ttnRepository) {
        this.waybillRepository = waybillRepository;
        this.checkPointRepository = checkPointRepository;
        this.ttnRepository = ttnRepository;
    }

    @Transactional
    @Override
    public Optional<WaybillDto> create(@Valid CreateWaybillDto createWaybillDto) {
        Waybill waybill = ObjectMapperUtills.mapTo(createWaybillDto, Waybill.class);
        waybill.getCheckPoints().forEach(cp -> cp.setCheckPointStatus(CheckPointStatus.NOT_PASSED));
        waybill.getTtn().setTtnState(TtnState.TRANSPORTATION_STARTED);

        return Optional.of(ObjectMapperUtills.mapTo(waybillRepository.save(waybill), WaybillDto.class));
    }

    @Transactional
    @Override
    public Optional<WaybillDto> update(long id, @Valid UpdateWaybillDto updateWaybillDto) {
        Waybill waybill = waybillRepository.getOne(id);
        ObjectMapperUtills.mapTo(updateWaybillDto, waybill);

        return Optional.of(ObjectMapperUtills.mapTo(waybillRepository.save(waybill), WaybillDto.class));
    }

    @Override
    public void updateDeleted(long id, boolean deleted) {
        Waybill waybill = waybillRepository.getOne(id);
        waybill.setDeleted(deleted);
        waybillRepository.save(waybill);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WaybillDto> find(long id) {
        return Optional.of(ObjectMapperUtills.mapTo(waybillRepository.findById(id).orElse(Waybill.builder().build()), WaybillDto.class));
    }

    @Transactional(readOnly = true)
    @Override
    public List<WaybillDto> findAllByPropsMatch(String searchParams) {
        List<WaybillDto> result = new ArrayList<>();
        if (!StringUtils.isBlank(searchParams)) {

            List<String> list = Arrays.stream(searchParams.trim().split(" "))
                    .filter(s -> !s.equals("")) //todo !s.equals("") check
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
