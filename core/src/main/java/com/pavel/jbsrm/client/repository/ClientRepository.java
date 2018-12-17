package com.pavel.jbsrm.client.repository;

import com.pavel.jbsrm.client.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>, ClientRepositoryCustom {
    Page<Client> findByDeleted(boolean deletedValue, Pageable pageable);
}
