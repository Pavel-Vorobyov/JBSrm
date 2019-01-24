package com.pavel.jbsrm.ttn.service;

import com.pavel.jbsrm.common.auth.UserPrinciple;
import com.pavel.jbsrm.common.utill.ObjectMapperUtills;
import com.pavel.jbsrm.product.product.ProductState;
import com.pavel.jbsrm.transport.TransportState;
import com.pavel.jbsrm.transport.repository.TransportRepository;
import com.pavel.jbsrm.ttn.QTtn;
import com.pavel.jbsrm.ttn.Ttn;
import com.pavel.jbsrm.ttn.TtnFilter;
import com.pavel.jbsrm.ttn.TtnState;
import com.pavel.jbsrm.ttn.dto.CreateTtnDto;
import com.pavel.jbsrm.ttn.dto.TtnDto;
import com.pavel.jbsrm.ttn.dto.TtnQuickSearchDto;
import com.pavel.jbsrm.ttn.dto.UpdateTtnDto;
import com.pavel.jbsrm.ttn.repository.TtnRepository;
import com.pavel.jbsrm.user.repository.UserRepository;
import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private TransportRepository transportRepository;
    private UserRepository userRepository;

    public TtnServiceImpl(TtnRepository ttnRepository, TransportRepository transportRepository, UserRepository userRepository) {
        this.ttnRepository = ttnRepository;
        this.transportRepository = transportRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public TtnDto create(@Valid CreateTtnDto createTtnDto) {
        Ttn ttn = ObjectMapperUtills.mapTo(createTtnDto, Ttn.class);
        ttn.setCreatedBy(userRepository.findById(((UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()).get());
        ttn.setDriver(userRepository.findById(createTtnDto.getDriverId()).get());
        ttn.setTransport(transportRepository.findById(createTtnDto.getTransportId()).get());
        ttn.setTtnState(TtnState.ACCEPTED);
        ttn.getCreatedBy().setId(((UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
        ttn.getDriver().setId(createTtnDto.getDriverId());
        ttn.getTransport().setId(createTtnDto.getTransportId());
        ttn.getTransport().setTransportState(TransportState.BUSY);
        ttn.getProducts().forEach(pr -> pr.setProductState(ProductState.ACCEPTED));
        ttn.setCreateAt(LocalDate.now());

        return ObjectMapperUtills.mapTo(ttnRepository.save(ttn), TtnDto.class);
    }

    @Transactional
    @Override
    public Optional<TtnDto> update(long id, @Valid UpdateTtnDto updateTtnDto) {
        Ttn ttn = ttnRepository.getOne(id);
        ObjectMapperUtills.mapTo(updateTtnDto, ttn);
        ttn.setProducts(updateTtnDto.getProducts());
        ttn.setId(id);
        ttn.setDriver(userRepository.getOne(updateTtnDto.getDriverId()));
        ttn.setTransport(transportRepository.getOne(updateTtnDto.getTransportId()));
        ttn.getDriver().setId(updateTtnDto.getDriverId()); //todo user.id from 4 to 0
        ttn.getTransport().setId(updateTtnDto.getTransportId());

        return Optional.of(ObjectMapperUtills.mapTo(ttnRepository.save(ttn), TtnDto.class));
    }

    @Transactional
    @Override
    public void updateDeleted(long id, boolean deleted) {
        Ttn ttn = ttnRepository.getOne(id);
        ttn.setDeleted(deleted);
        ttnRepository.save(ttn);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TtnDto> find(long id) {
        return Optional.of(ObjectMapperUtills
                .mapTo(ttnRepository.findById(id).orElse(Ttn.builder().build()), TtnDto.class));
    }

    @Transactional(readOnly = true)
    @Override
    public List<TtnQuickSearchDto> findAllByPropsMatch(String searchParams) {
        List<TtnQuickSearchDto> result = new ArrayList<>();
        if (StringUtils.isNotBlank(searchParams)) {

            List<String> list = Arrays.stream(searchParams.trim().split(" "))
                    .filter(s -> !s.equals(""))
                    .collect(Collectors.toList());

            result = ttnRepository.findAllByPropsMatch(list);
        }
        return result;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<TtnDto> findAllPageByFilter(TtnFilter filter, Pageable pageable) {
        return ttnRepository.findAll(buildFilter(filter), pageable)
                .map(ttn -> ObjectMapperUtills.mapTo(ttn, TtnDto.class));
    }

    private BooleanBuilder buildFilter(TtnFilter filter) {
        BooleanBuilder whereBuilder = new BooleanBuilder();

        if (filter != null) {
            if (filter.getDeleted() != null) {
                whereBuilder.and(QTtn.ttn.deleted.eq(filter.getDeleted()));
            }
            if (filter.getTtnState() != null) {
                whereBuilder.and(QTtn.ttn.ttnState.eq(filter.getTtnState()));
            }
        }
        return whereBuilder;
    }
}
