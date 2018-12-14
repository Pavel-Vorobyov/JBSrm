package com.pavel.jbsrm.transport.repository;

import com.pavel.jbsrm.transport.Transport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportRepository extends PagingAndSortingRepository<Transport, Long>, TransportRepositoryCustom {
    Page<Transport> findByIsDeleted(boolean isDeletedValue, Pageable pageable);
}
