package com.pavel.jbsrm.deed.service;

import com.pavel.jbsrm.common.utill.ObjectMapperUtills;
import com.pavel.jbsrm.deed.Deed;
import com.pavel.jbsrm.deed.dto.CreateDeedDto;
import com.pavel.jbsrm.deed.dto.DeedDto;
import com.pavel.jbsrm.deed.dto.UpdateDeedDto;
import com.pavel.jbsrm.deed.repository.DeedRepository;
import com.pavel.jbsrm.product.details.repository.ProductDetailsRepository;
import com.pavel.jbsrm.product.product.Product;
import com.pavel.jbsrm.product.product.repository.ProductRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeedServiceImpl implements DeedService {

    private DeedRepository deedRepository;
    private ProductRepository productRepository;
    private ProductDetailsRepository productDetailsRepository;

    public DeedServiceImpl(DeedRepository deedRepository, ProductRepository productRepository, ProductDetailsRepository productDetailsRepository) {
        this.deedRepository = deedRepository;
        this.productRepository = productRepository;
        this.productDetailsRepository = productDetailsRepository;
    }

    @Transactional
    @Override
    public DeedDto create(@Valid CreateDeedDto createDeedDto) {
        Deed deed = ObjectMapperUtills.mapTo(createDeedDto, Deed.class);
        Product product = new Product();
        product.setProductDetails(productDetailsRepository.getOne(createDeedDto.getProductDetailsId()));
        product.setAmount(createDeedDto.getAmount());
        deed.setProduct(productRepository.save(product));
        deed.setId(0L);
        return ObjectMapperUtills.mapTo(deedRepository.save(deed), DeedDto.class);
    }

    @Transactional
    @Override
    public Optional<DeedDto> update(long id, @Valid UpdateDeedDto updateDeedDto) {
        Deed deed = deedRepository.getOne(id);
        ObjectMapperUtills.mapTo(updateDeedDto, deed);
        Product product = productRepository.getOne(updateDeedDto.getProductId());
        product.setAmount(updateDeedDto.getAmount());
        deed.setProduct(product);

        return Optional.of(ObjectMapperUtills.mapTo(deedRepository.save(deed), DeedDto.class));
    }

    @Override
    public void updateDeleted(long id, boolean deleted) {
        Deed deed = deedRepository.getOne(id);
        deed.setDeleted(deleted);
        deedRepository.save(deed);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DeedDto> find(long id) {
        return Optional.of(ObjectMapperUtills.mapTo(deedRepository.findById(id).orElse(Deed.builder().build()), DeedDto.class));
    }

    @Transactional(readOnly = true)
    @Override
    public List<DeedDto> findAllByPropsMatch(String searchParams) {
        List<DeedDto> result = new ArrayList<>();
        if (!StringUtils.isBlank(searchParams)) {

            List<String> list = Arrays.stream(searchParams.trim().split(" "))
                    .filter(s -> !s.equals(""))
                    .collect(Collectors.toList());

            deedRepository.findAllByPropsMatch(list)
                    .forEach(deed -> result.add(ObjectMapperUtills.mapTo(deed, DeedDto.class)));
        }
        return result;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<DeedDto> findAllPageByFilter(boolean deleted, Pageable pageable) {
        return deedRepository.findByDeleted(deleted, pageable)
                .map(deed -> ObjectMapperUtills.mapTo(deed, DeedDto.class));
    }
}
