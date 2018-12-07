package com.pavel.jbsrm.common.service;

import javax.validation.Valid;

public interface AbstractCrudService<CreateDto, UpdateDto, ResultClass> {

    ResultClass createClient(@Valid CreateDto createClientDto);

    ResultClass updateClient(long id, @Valid UpdateDto updateClientDto);

    void deleteClient(long id);

    ResultClass find(long id);
}
