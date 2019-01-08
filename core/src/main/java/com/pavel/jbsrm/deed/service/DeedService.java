package com.pavel.jbsrm.deed.service;

import com.pavel.jbsrm.deed.dto.CreateDeedDto;
import com.pavel.jbsrm.deed.dto.DeedDto;
import com.pavel.jbsrm.deed.dto.UpdateDeedDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Validated
public interface DeedService {

    Optional<DeedDto> create(@Valid CreateDeedDto createDeedDto);

    Optional<DeedDto> update(long id, @Valid UpdateDeedDto updateDeedDto);

    void updateDeleted(long id, boolean deleted);

    Optional<DeedDto> find(long id);

    List<DeedDto> findAllByPropsMatch(String searchParams);

    Page<DeedDto> findAllPageByDeleted(boolean deleted, Pageable pageable);
}
