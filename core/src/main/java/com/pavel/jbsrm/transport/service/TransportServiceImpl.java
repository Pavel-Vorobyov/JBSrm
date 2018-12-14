package com.pavel.jbsrm.transport.service;

import com.pavel.jbsrm.common.utill.ObjectMapperUtills;
import com.pavel.jbsrm.product.Product;
import com.pavel.jbsrm.product.ProductState;
import com.pavel.jbsrm.product.dto.CreateProductDto;
import com.pavel.jbsrm.product.dto.ProductDto;
import com.pavel.jbsrm.product.dto.UpdateProductDto;
import com.pavel.jbsrm.product.repository.ProductRepository;
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

@Service
public class TransportServiceImpl implements TransportService {

    @Autowired
    private TransportRepository transportRepository;


    @Override
    public TransportDto createTransport(@Valid CreateTransportDto createTransportDto) {
        Transport transport = ObjectMapperUtills.mapTo(createTransportDto, Transport.class);
        return ObjectMapperUtills.mapTo(transportRepository.save(transport), TransportDto.class);
    }

    @Override
    public TransportDto updateTransport(long id, @Valid UpdateTransportDto updateTransportDto) {
        Transport transport = ObjectMapperUtills.mapTo(updateTransportDto, Transport.class);
        return ObjectMapperUtills.mapTo(transportRepository.save(transport), TransportDto.class);
    }

    @Override
    public void deleteTransport(long id) {
        updateDeletedStatus(id, true);
    }

    @Override
    public void restoreTransport(long id) {
        updateDeletedStatus(id, false);
    }

    private void updateDeletedStatus(long id, boolean isDeleted) {
        Optional<Transport> transport = transportRepository.findById(id);
        transport.ifPresent(p -> {
            p.setDeleted(isDeleted);
            transportRepository.save(transport.get());
        });
    }

    @Override
    public TransportDto find(long id) {
        return ObjectMapperUtills.mapTo(transportRepository.findById(id), TransportDto.class);
    }

    @Override
    public List<TransportDto> findAllByPropsMatch(String searchParams) {
        List<TransportDto> result = new ArrayList<>();
        if (!StringUtils.isBlank(searchParams)) {

            String[] parsedSearchParams = searchParams.trim().split(" ");
            List<String> list = new ArrayList<>(Arrays.asList(parsedSearchParams));
            list.removeAll(Arrays.asList("", null));

            transportRepository.findAllByPropsMatch(list)
                    .forEach(t -> result.add(ObjectMapperUtills.mapTo(t, TransportDto.class)));
        }
        return result;
    }

    @Override
    public Page<TransportDto> findAllPageByDeleted(boolean isDeleted, Pageable pageable) {
        return transportRepository.findByIsDeleted(isDeleted, pageable)
                .map(transport -> ObjectMapperUtills.mapTo(transport, TransportDto.class));
    }
}
