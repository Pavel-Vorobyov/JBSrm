package com.pavel.jbsrm.transport.repository;

import com.pavel.jbsrm.transport.Transport;

import java.util.List;

public interface TransportRepositoryCustom {

    List<Transport> findAllByPropsMatch(List<String> props);
}
