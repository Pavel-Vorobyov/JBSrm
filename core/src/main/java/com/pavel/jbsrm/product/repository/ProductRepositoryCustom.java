package com.pavel.jbsrm.product.repository;

import com.pavel.jbsrm.product.Product;

import java.util.List;

public interface ProductRepositoryCustom {

    List<Product> findAllByPropsMatch(List<String> props);
}
