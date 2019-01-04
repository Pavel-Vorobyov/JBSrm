package com.pavel.jbsrm.product.product.repository;

import com.pavel.jbsrm.product.product.dto.ProductQuickSearchDto;

import java.util.List;

public interface ProductRepositoryCustom {

    List<ProductQuickSearchDto> findAllByPropsMatch(List<String> props);
}
