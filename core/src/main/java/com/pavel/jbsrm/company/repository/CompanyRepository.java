package com.pavel.jbsrm.company.repository;

import com.pavel.jbsrm.company.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long>, CompanyRepositoryCustom {
    Page<Company> findByDeleted(boolean deletedValue, Pageable pageable);
}
