package com.pavel.jbsrm.product.details.service;

import com.pavel.jbsrm.common.utill.ObjectMapperUtills;
import com.pavel.jbsrm.product.details.ProductDetails;
import com.pavel.jbsrm.product.details.ProductDetailsFilter;
import com.pavel.jbsrm.product.details.QProductDetails;
import com.pavel.jbsrm.product.details.dto.CreateProductDetailsDto;
import com.pavel.jbsrm.product.details.dto.ProductDetailsDto;
import com.pavel.jbsrm.product.details.dto.UpdateProductDetailsDto;
import com.pavel.jbsrm.product.details.repository.ProductDetailsRepository;
import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductDetailsServiceImpl implements ProductDetailsService {

    @Autowired
    private ProductDetailsRepository productDetailsRepository;


    @Override
    public ProductDetailsDto create(@Valid CreateProductDetailsDto createProductDto) {
        ProductDetails product = ObjectMapperUtills.mapTo(createProductDto, ProductDetails.class);
        return ObjectMapperUtills.mapTo(productDetailsRepository.save(product), ProductDetailsDto.class);
    }

    @Override
    public Optional<ProductDetailsDto> update(long id, @Valid UpdateProductDetailsDto updateProductDto) {
        ProductDetails productDetails = productDetailsRepository.getOne(id);
        ObjectMapperUtills.mapTo(updateProductDto, productDetails);

        return Optional.of(ObjectMapperUtills.mapTo(productDetailsRepository.save(productDetails), ProductDetailsDto.class));
    }

    @Override
    public void updateDeleted(long id, boolean deleted) {
        ProductDetails product = productDetailsRepository.getOne(id);
        product.setDeleted(deleted);
        productDetailsRepository.save(product);
    }

    @Override
    public Optional<ProductDetailsDto> find(long id) {
        return Optional.of(ObjectMapperUtills.mapTo(productDetailsRepository.findById(id).get(), ProductDetailsDto.class));
    }

    @Override
    public List<ProductDetailsDto> findAllByPropsMatch(String searchParams) {
        List<ProductDetailsDto> result = new ArrayList<>();
        if (!StringUtils.isBlank(searchParams)) {

            List<String> list = Arrays.stream(searchParams.trim().split(" "))
                    .filter(s -> !s.equals(""))
                    .collect(Collectors.toList());

            productDetailsRepository.findAllByPropsMatch(list)
                    .forEach(product -> result.add(ObjectMapperUtills.mapTo(product, ProductDetailsDto.class)));
        }
        return result;
    }

    @Override
    public Page<ProductDetailsDto> findAllPageByFilter(ProductDetailsFilter filter, Pageable pageable) {
        return productDetailsRepository.findAll(buildFilter(filter), pageable)
                .map(product -> ObjectMapperUtills.mapTo(product, ProductDetailsDto.class));
    }

    private BooleanBuilder buildFilter(ProductDetailsFilter filter) {
        BooleanBuilder whereBuilder = new BooleanBuilder();

        if (filter != null) {
            if (filter.getDeleted() != null) {
                whereBuilder.and(QProductDetails.productDetails.deleted.eq(filter.getDeleted()));
            }
            if (filter.getTitle() != null) {
                whereBuilder.and(QProductDetails.productDetails.title.eq(filter.getTitle()));
            }
        }
        return whereBuilder;
    }
}
