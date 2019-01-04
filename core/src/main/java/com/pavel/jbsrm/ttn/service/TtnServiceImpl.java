package com.pavel.jbsrm.ttn.service;

import com.pavel.jbsrm.common.exception.ResourceNotFoundException;
import com.pavel.jbsrm.common.utill.ObjectMapperUtills;
import com.pavel.jbsrm.product.product.repository.ProductRepository;
import com.pavel.jbsrm.ttn.QTtn;
import com.pavel.jbsrm.ttn.Ttn;
import com.pavel.jbsrm.ttn.TtnFilter;
import com.pavel.jbsrm.ttn.TtnState;
import com.pavel.jbsrm.ttn.dto.CreateTtnDto;
import com.pavel.jbsrm.ttn.dto.TtnDto;
import com.pavel.jbsrm.ttn.dto.TtnQuickSearchDto;
import com.pavel.jbsrm.ttn.dto.UpdateTtnDto;
import com.pavel.jbsrm.ttn.repository.TtnRepository;
import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TtnServiceImpl implements TtnService {

    private TtnRepository ttnRepository;
    private ProductRepository productRepository;

    @Autowired
    public TtnServiceImpl(TtnRepository ttnRepository, ProductRepository productRepository) {
        this.ttnRepository = ttnRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    @Override
    public TtnDto create(@Valid CreateTtnDto createTtnDto) {
        Ttn ttn = ObjectMapperUtills.mapTo(createTtnDto, Ttn.class);
        ttn.setCreateAt(LocalDate.now());
        ttn.setCreatedBy(createTtnDto.getDriver());
        ttn.setTtnState(TtnState.ACCEPTED);
        return ObjectMapperUtills.mapTo(ttnRepository.save(ttn), TtnDto.class);
    }

    @Transactional
    @Override
    public TtnDto update(long id, @Valid UpdateTtnDto updateTtnDto) throws ResourceNotFoundException {
        Optional<Ttn> ttn = Optional.of(ttnRepository.getOne(id));
        ttn.ifPresent(t -> ObjectMapperUtills.mapTo(updateTtnDto, t));

        return ObjectMapperUtills.mapTo(
                ttn.map(t -> ttnRepository.save(t)).orElseThrow(ResourceNotFoundException::new), TtnDto.class);
    }

    @Transactional
    @Override
    public void updateDeleted(long id, boolean deleted) throws ResourceNotFoundException {
        Optional<Ttn> ttn = Optional.of(ttnRepository.getOne(id));
        ttn.orElseThrow(ResourceNotFoundException::new)
                .setDeleted(deleted);
        ttnRepository.save(ttn.get());
    }

    @Override
    @Transactional(readOnly = true)
    public TtnDto find(long id) {
        return ObjectMapperUtills.mapTo(ttnRepository.findById(id).orElse(Ttn.builder().build()), TtnDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public List<TtnQuickSearchDto> findAllByPropsMatch(String searchParams) {
        List<TtnQuickSearchDto> result = new ArrayList<>();
        if (StringUtils.isNotBlank(searchParams)) {

            List<String> list = Arrays.stream(searchParams.trim().split(" "))
                    .filter(s -> !s.equals("")) //todo !s.equals("") check
                    .collect(Collectors.toList());

            result = ttnRepository.findAllByPropsMatch(list);
        }
        return result;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<TtnDto> findAllPageByDeleted(TtnFilter filter, Pageable pageable) {
        return ttnRepository.findAll(buildFilter(filter), pageable)
                .map(ttn -> ObjectMapperUtills.mapTo(ttn, TtnDto.class));
    }

    private BooleanBuilder buildFilter(TtnFilter filter) {
        BooleanBuilder whereBuilder = new BooleanBuilder();

        if (filter != null) {
            if (filter.getDeleted() != null) {
                whereBuilder.and(QTtn.ttn.deleted.eq(filter.getDeleted()));
            }
        }
        return whereBuilder;
    }
}
