package com.pavel.jbsrm.product.details.repository;

import com.pavel.jbsrm.product.details.ProductDetails;

import java.util.List;

public interface ProductDetailsRepositoryCustom {

    List<ProductDetails> findAllByPropsMatch(List<String> props);
}
