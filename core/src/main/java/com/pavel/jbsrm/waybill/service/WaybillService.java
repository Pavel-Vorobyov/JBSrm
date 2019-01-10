package com.pavel.jbsrm.waybill.service;

import com.pavel.jbsrm.waybill.dto.CreateWaybillDto;
import com.pavel.jbsrm.waybill.dto.UpdateWaybillDto;
import com.pavel.jbsrm.waybill.dto.WaybillDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Validated
public interface WaybillService {

    WaybillDto create(@Valid CreateWaybillDto createWaybillDto);

    Optional<WaybillDto> update(long id, @Valid UpdateWaybillDto updateWaybillDto);

    void updateDeleted(long id, boolean deleted);

    Optional<WaybillDto> find(long id);

    List<WaybillDto> findAllByPropsMatch(String searchParams);

    Page<WaybillDto> findAllPageByFilter(boolean deleted, Pageable pageable);
}
