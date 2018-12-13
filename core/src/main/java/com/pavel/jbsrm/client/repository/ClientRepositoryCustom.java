package com.pavel.jbsrm.client.repository;

import com.pavel.jbsrm.client.Client;

import java.util.List;

public interface ClientRepositoryCustom {

    List<Client> findAllByPropsMatch(List<String> props);
}
