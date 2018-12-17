package com.pavel.jbsrm.company.repository;

import com.pavel.jbsrm.company.Company;

import java.util.List;

public interface CompanyRepositoryCustom {

    List<Company> findAllByPropsMatch(List<String> props);
}
