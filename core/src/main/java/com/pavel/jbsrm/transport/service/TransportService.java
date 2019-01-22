package com.pavel.jbsrm.transport.service;

import com.pavel.jbsrm.transport.dto.CreateTransportDto;
import com.pavel.jbsrm.transport.dto.TransportDto;
import com.pavel.jbsrm.transport.TransportFilter;
import com.pavel.jbsrm.transport.dto.UpdateTransportDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Validated
public interface TransportService {

    TransportDto create(@Valid CreateTransportDto createTransportDto);

    Optional<TransportDto> update(long id, @Valid UpdateTransportDto updateTransportDto);

    void updateDeleted(long id, boolean deleted);

    Optional<TransportDto> find(long id);

    List<TransportDto> findAllByPropsMatch(String searchParams);

    Page<TransportDto> findAllPageByFilter(TransportFilter filter, Pageable pageable);
}
