package com.pavel.jbsrm.ttn.service;

import com.pavel.jbsrm.common.exception.ResourceNotFoundException;
import com.pavel.jbsrm.ttn.TtnFilter;
import com.pavel.jbsrm.ttn.dto.CreateTtnDto;
import com.pavel.jbsrm.ttn.dto.TtnDto;
import com.pavel.jbsrm.ttn.dto.UpdateTtnDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface TtnService {

    TtnDto create(@Valid CreateTtnDto createTtnDto);

    TtnDto update(long id, @Valid UpdateTtnDto updateTtnDto) throws ResourceNotFoundException;

    void updateDeleted(long id, boolean deleted) throws ResourceNotFoundException;

    TtnDto find(long id);

    List<TtnDto> findAllByPropsMatch(String searchParams);

    Page<TtnDto> findAllPageByDeleted(TtnFilter filter, Pageable pageable);
}
