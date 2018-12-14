package com.pavel.jbsrm.client.repository;

import com.pavel.jbsrm.client.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends PagingAndSortingRepository<Client, Long>, ClientRepositoryCustom {
    Page<Client> findByIsDeleted(boolean isDeletedValue, Pageable pageable);
}
