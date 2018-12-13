package com.pavel.jbsrm.common.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;
import java.util.List;

public interface AbstractCrudService<CreateDto, UpdateDto, ResultDto, Entity> {

    ResultDto createEntity(@Valid CreateDto createEntityDto);

    ResultDto updateEntity(long id, @Valid UpdateDto updateEntityDto);

    void deleteEntity(long id);

    void restoreClient(long id);

    ResultDto find(long id);

    List<ResultDto> findAllByPropsMatch(String searchParams);

    Page<ResultDto> findAllPageByActive(boolean isActive, Pageable pageable);
}
