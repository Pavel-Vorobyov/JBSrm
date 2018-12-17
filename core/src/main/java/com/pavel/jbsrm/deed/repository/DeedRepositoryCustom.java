package com.pavel.jbsrm.deed.repository;

import com.pavel.jbsrm.deed.Deed;

import java.util.List;

public interface DeedRepositoryCustom {

    List<Deed> findAllByPropsMatch(List<String> props);
}
