package com.pavel.jbsrm.product.repository;

import com.pavel.jbsrm.client.Client;

import java.util.List;

public interface ProductRepositoryCustom {

    List<Client> findAllByPropsMatch(List<String> props);
}
