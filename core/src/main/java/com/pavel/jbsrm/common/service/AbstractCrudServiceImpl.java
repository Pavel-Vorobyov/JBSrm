package com.pavel.jbsrm.common.service;

import org.springframework.data.repository.CrudRepository;

import javax.validation.Valid;

public class AbstractCrudServiceImpl <CreateDto, UpdateDto, ResultClass> implements AbstractCrudService<CreateDto, UpdateDto, ResultClass> {

    private CrudRepository crudRepository;

    public AbstractCrudServiceImpl(CrudRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public ResultClass createClient(@Valid CreateDto createClientDto) {
        return null;
    }

    @Override
    public ResultClass updateClient(long id, @Valid UpdateDto updateClientDto) {
        return null;
    }

    @Override
    public void deleteClient(long id) {

    }

    @Override
    public ResultClass find(long id) {
        return null;
    }
}
