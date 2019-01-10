package com.pavel.jbsrm.ttn.service;

import com.pavel.jbsrm.ttn.TtnFilter;
import com.pavel.jbsrm.ttn.dto.CreateTtnDto;
import com.pavel.jbsrm.ttn.dto.TtnDto;
import com.pavel.jbsrm.ttn.dto.TtnQuickSearchDto;
import com.pavel.jbsrm.ttn.dto.UpdateTtnDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Validated
public interface TtnService {

    TtnDto create(@Valid CreateTtnDto createTtnDto);

    Optional<TtnDto> update(long id, @Valid UpdateTtnDto updateTtnDto);

    void updateDeleted(long id, boolean deleted);

    Optional<TtnDto> find(long id);

    List<TtnQuickSearchDto> findAllByPropsMatch(String searchParams);

    Page<TtnDto> findAllPageByFilter(TtnFilter filter, Pageable pageable);



}
