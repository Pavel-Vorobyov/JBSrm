package com.pavel.jbsrm.transport.service;

import com.pavel.jbsrm.common.exception.ResourceNotFoundException;
import com.pavel.jbsrm.common.utill.ObjectMapperUtills;
import com.pavel.jbsrm.transport.Transport;
import com.pavel.jbsrm.transport.dto.CreateTransportDto;
import com.pavel.jbsrm.transport.dto.TransportDto;
import com.pavel.jbsrm.transport.dto.UpdateTransportDto;
import com.pavel.jbsrm.transport.repository.TransportRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private TransportRepository transportRepository;


    @Override
    public TransportDto create(@Valid CreateTransportDto createTransportDto) {
        Transport transport = ObjectMapperUtills.mapTo(createTransportDto, Transport.class);
        return ObjectMapperUtills.mapTo(transportRepository.save(transport), TransportDto.class);
    }

    @Override
    public TransportDto update(long id, @Valid UpdateTransportDto updateTransportDto) {
        Optional<Transport> transport = Optional.of(transportRepository.getOne(id));
        transport.ifPresent(tr -> ObjectMapperUtills.mapTo(updateTransportDto, tr));
        return ObjectMapperUtills.mapTo(transport.map(
                tr -> transportRepository.save(tr)).orElseThrow(ResourceNotFoundException::new), TransportDto.class);
    }

    @Override
    public void updateDeleted(long id, boolean deleted) throws ResourceNotFoundException {
        Optional<Transport> transport = Optional.of(transportRepository.getOne(id));
        transport.orElseThrow(ResourceNotFoundException::new)
            .setDeleted(deleted);
        transportRepository.save(transport.get());
    }

    @Override
    public TransportDto find(long id) {
        return ObjectMapperUtills.mapTo(transportRepository.findById(id).orElse(Transport.builder().build()), TransportDto.class);
    }

    @Override
    public List<TransportDto> findAllByPropsMatch(String searchParams) {
        List<TransportDto> result = new ArrayList<>();
        if (!StringUtils.isBlank(searchParams)) {

            List<String> list  = Arrays.stream(searchParams.trim().split(" "))
                    .filter(s -> !s.equals(""))
                    .collect(Collectors.toList());

            transportRepository.findAllByPropsMatch(list)
                    .forEach(t -> result.add(ObjectMapperUtills.mapTo(t, TransportDto.class)));
        }
        return result;
    }

    @Override
    public Page<TransportDto> findAllPageByDeleted(boolean deleted, Pageable pageable) {
        return transportRepository.findByDeleted(deleted, pageable)
                .map(transport -> ObjectMapperUtills.mapTo(transport, TransportDto.class));
    }
}
