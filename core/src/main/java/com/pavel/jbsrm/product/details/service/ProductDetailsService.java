package com.pavel.jbsrm.product.details.service;

import com.pavel.jbsrm.product.details.ProductDetailsFilter;
import com.pavel.jbsrm.product.details.dto.CreateProductDetailsDto;
import com.pavel.jbsrm.product.details.dto.ProductDetailsDto;
import com.pavel.jbsrm.product.details.dto.UpdateProductDetailsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Validated
public interface ProductDetailsService {

    Optional<ProductDetailsDto> create(@Valid CreateProductDetailsDto createProductDto);

    Optional<ProductDetailsDto> update(long id, @Valid UpdateProductDetailsDto updateProductDto);

    void updateDeleted(long id, boolean deleted);

    Optional<ProductDetailsDto> find(long id);

    List<ProductDetailsDto> findAllByPropsMatch(String searchParams);

    Page<ProductDetailsDto> findAllPageByDeleted(ProductDetailsFilter filter, Pageable pageable);
}
