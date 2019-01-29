package com.pavel.jbsrm.company.repository;

import com.pavel.jbsrm.company.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

public interface CompanyRepository extends JpaRepository<Company, Long>, QuerydslPredicateExecutor<Company>, CompanyRepositoryCustom {
    Page<Company> findByDeleted(boolean deleted, Pageable pageable);
}
