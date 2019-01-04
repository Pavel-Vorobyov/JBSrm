package com.pavel.jbsrm.product.product.service;

import com.pavel.jbsrm.product.product.ProductFilter;
import com.pavel.jbsrm.product.product.dto.CreateProductDto;
import com.pavel.jbsrm.product.product.dto.ProductDto;
import com.pavel.jbsrm.product.product.dto.ProductQuickSearchDto;
import com.pavel.jbsrm.product.product.dto.UpdateProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Validated
public interface ProductService {

    Optional<ProductDto> create(@Valid CreateProductDto createProductDto);

    Optional<ProductDto> update(long id, @Valid UpdateProductDto updateProductDto);

    void updateDeleted(long id, boolean deleted);

    Optional<ProductDto> find(long id);

    List<ProductQuickSearchDto> findAllByPropsMatch(String searchParams);

    Page<ProductDto> findAllPageByDeleted(ProductFilter filter, Pageable pageable);
}
