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

    ProductDto createProduct(@Valid CreateProductDto createProductDto);

    ProductDto updateProduct(long id, @Valid UpdateProductDto updateProductDto);

    void deleteProduct(long id);

    void restoreProduct(long id);

    ProductDto find(long id);

    List<ProductDto> findAllByPropsMatch(String searchParams);

    Page<ProductDto> findAllPageByDeleted(boolean isDeleted, Pageable pageable);
}
