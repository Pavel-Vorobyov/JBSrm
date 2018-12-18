package com.pavel.jbsrm.waybill.service;

import com.pavel.jbsrm.common.exception.ResourceNotFoundException;
import com.pavel.jbsrm.waybill.dto.CreateWaybillDto;
import com.pavel.jbsrm.waybill.dto.UpdateWaybillDto;
import com.pavel.jbsrm.waybill.dto.WaybillDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface WaybillService {

    WaybillDto create(@Valid CreateWaybillDto createWaybillDto);

    WaybillDto update(long id, @Valid UpdateWaybillDto updateWaybillDto) throws ResourceNotFoundException;

    void updateDeleted(long id, boolean deleted) throws ResourceNotFoundException;

    WaybillDto find(long id);

    List<WaybillDto> findAllByPropsMatch(String searchParams);

    Page<WaybillDto> findAllPageByDeleted(boolean deleted, Pageable pageable);
}
