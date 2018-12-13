package com.pavel.jbsrm.common.service;

import com.pavel.jbsrm.common.utill.ObjectMapperUtills;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.validation.Valid;
import java.util.List;

public class AbstractCrudServiceImpl <CreateDto, UpdateDto, ResultDto, Entity> implements AbstractCrudService<CreateDto, UpdateDto, ResultDto, Entity> {

    private PagingAndSortingRepository entityRepository;

    private Class<? extends CreateDto> createDtoClass;
    private Class<? extends UpdateDto> updateDtoClass;
    private Class<? extends ResultDto> resultDtoClass;
    private Class<? extends Entity> entityClass;

    public AbstractCrudServiceImpl(PagingAndSortingRepository entityRepository,
                                   Class<? extends CreateDto> createDtoClass,
                                   Class<? extends UpdateDto> updateDtoClass,
                                   Class<? extends ResultDto> resultDtoClass,
                                   Class<? extends Entity> entityClass) {
        this.entityRepository = entityRepository;
        this.createDtoClass = createDtoClass;
        this.updateDtoClass = updateDtoClass;
        this.resultDtoClass = resultDtoClass;
        this.entityClass = entityClass;
    }

    @Override
    public ResultDto createEntity(@Valid CreateDto createEntityDto) {
        Entity client = ObjectMapperUtills.mapTo(createEntityDto, entityClass);
//        client(true);
        return ObjectMapperUtills.mapTo(entityRepository.save(client), resultDtoClass);
    }

    @Override
    public ResultDto updateEntity(long id, @Valid UpdateDto updateEntityDto) {
        return null;
    }

    @Override
    public void deleteEntity(long id) {

    }

    @Override
    public void restoreClient(long id) {

    }

    @Override
    public ResultDto find(long id) {
        return null;
    }

    @Override
    public List<ResultDto> findAllByPropsMatch(String searchParams) {
        return null;
    }

    @Override
    public Page<ResultDto> findAllPageByActive(boolean isActive, Pageable pageable) {
        return null;
    }
}
