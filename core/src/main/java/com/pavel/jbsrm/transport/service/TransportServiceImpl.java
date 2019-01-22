package com.pavel.jbsrm.transport.service;

import com.pavel.jbsrm.common.utill.ObjectMapperUtills;
import com.pavel.jbsrm.company.repository.CompanyRepository;
import com.pavel.jbsrm.transport.QTransport;
import com.pavel.jbsrm.transport.Transport;
import com.pavel.jbsrm.transport.dto.CreateTransportDto;
import com.pavel.jbsrm.transport.dto.TransportDto;
import com.pavel.jbsrm.transport.TransportFilter;
import com.pavel.jbsrm.transport.dto.UpdateTransportDto;
import com.pavel.jbsrm.transport.repository.TransportRepository;
import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransportServiceImpl implements TransportService {

    private TransportRepository transportRepository;
    private CompanyRepository companyRepository;

    public TransportServiceImpl(TransportRepository transportRepository, CompanyRepository companyRepository) {
        this.transportRepository = transportRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public TransportDto create(@Valid CreateTransportDto createTransportDto) {
        Transport transportToCreate = ObjectMapperUtills.mapTo(createTransportDto, Transport.class);
        transportToCreate.setOwner(companyRepository.findById(createTransportDto.getOwnerId()).get());
        transportToCreate.getOwner().setId(createTransportDto.getOwnerId());
        return ObjectMapperUtills.mapTo(transportRepository.save(transportToCreate), TransportDto.class);
    }

    @Override
    public Optional<TransportDto> update(long id, @Valid UpdateTransportDto updateTransportDto) {
        Transport transport = transportRepository.getOne(id);
        ObjectMapperUtills.mapTo(updateTransportDto, transport);
//        transport.setId(id);
        transport.setOwner(companyRepository.getOne(updateTransportDto.getOwnerId())); //todo company.id from 3 to 0
//        transport.getOwner().setId(updateTransportDto.getOwnerId());
        return Optional.of(ObjectMapperUtills.mapTo(transportRepository.save(transport), TransportDto.class));
    }

    @Override
    public void updateDeleted(long id, boolean deleted) {
        Transport transport = transportRepository.getOne(id);
        transport.setDeleted(deleted);
        transportRepository.save(transport);
    }

    @Override
    public Optional<TransportDto> find(long id) {
        return Optional.of(ObjectMapperUtills
                .mapTo(transportRepository.findById(id).orElse(Transport.builder().build()), TransportDto.class));
    }

    @Override
    public List<TransportDto> findAllByPropsMatch(String searchParams) {
        List<TransportDto> result = new ArrayList<>();
        if (!StringUtils.isBlank(searchParams)) {

            List<String> list = Arrays.stream(searchParams.trim().split(" "))
                    .filter(s -> !s.equals(""))
                    .collect(Collectors.toList());

            transportRepository.findAllByPropsMatch(list)
                    .forEach(t -> result.add(ObjectMapperUtills.mapTo(t, TransportDto.class)));
        }
        return result;
    }

    @Override
    public Page<TransportDto> findAllPageByFilter(TransportFilter filter, Pageable pageable) {
        return transportRepository.findAll(buildFilter(filter), pageable)
                .map(transport -> ObjectMapperUtills.mapTo(transport, TransportDto.class));
    }

    private BooleanBuilder buildFilter(TransportFilter filter) {
        BooleanBuilder whereBuilder = new BooleanBuilder();

        if (filter != null) {
            if (filter.getDeleted() != null) {
                whereBuilder.and(QTransport.transport.deleted.eq(filter.getDeleted()));
            }
            if (filter.getTransportState() != null) {
                whereBuilder.and(QTransport.transport.transportState.eq(filter.getTransportState()));
            }
        }
        return whereBuilder;
    }
}
