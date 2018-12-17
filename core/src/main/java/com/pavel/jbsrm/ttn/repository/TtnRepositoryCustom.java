package com.pavel.jbsrm.ttn.repository;

import com.pavel.jbsrm.ttn.Ttn;

import java.util.List;

public interface TtnRepositoryCustom {

    List<Ttn> findAllByPropsMatch(List<String> props);
}
