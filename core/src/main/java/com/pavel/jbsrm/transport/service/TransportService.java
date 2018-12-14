package com.pavel.jbsrm.transport.service;

import com.pavel.jbsrm.transport.dto.CreateTransportDto;
import com.pavel.jbsrm.transport.dto.TransportDto;
import com.pavel.jbsrm.transport.dto.UpdateTransportDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface TransportService {

    TransportDto createTransport(@Valid CreateTransportDto createTransportDto);

    TransportDto updateTransport(long id, @Valid UpdateTransportDto updateTransportDto);

    void deleteTransport(long id);

    void restoreTransport(long id);

    TransportDto find(long id);

    List<TransportDto> findAllByPropsMatch(String searchParams);

    Page<TransportDto> findAllPageByDeleted(boolean isDeleted, Pageable pageable);
}
