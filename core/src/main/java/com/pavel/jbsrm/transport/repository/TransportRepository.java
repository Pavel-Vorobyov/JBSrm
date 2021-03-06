package com.pavel.jbsrm.transport.repository;

import com.pavel.jbsrm.transport.Transport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

public interface TransportRepository extends JpaRepository<Transport, Long>, QuerydslPredicateExecutor<Transport>, TransportRepositoryCustom {
    Page<Transport> findByDeleted(boolean deletedValue, Pageable pageable);
}
