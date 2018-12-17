package com.pavel.jbsrm.product.service;

import com.pavel.jbsrm.product.dto.CreateProductDto;
import com.pavel.jbsrm.product.dto.ProductDto;
import com.pavel.jbsrm.product.dto.UpdateProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface ProductService {

    ProductDto create(@Valid CreateProductDto createProductDto);

    ProductDto update(long id, @Valid UpdateProductDto updateProductDto);

    void updateDeleted(long id, boolean deleted);

    ProductDto find(long id);

    List<ProductDto> findAllByPropsMatch(String searchParams);

    Page<ProductDto> findAllPageByDeleted(boolean deleted, Pageable pageable);
}
