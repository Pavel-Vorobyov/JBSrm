package com.pavel.jbsrm.ttn.repository;

import com.pavel.jbsrm.ttn.Ttn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TtnRepository extends JpaRepository<Ttn, Long>, TtnRepositoryCustom {
    Page<Ttn> findByDeleted(boolean deletedValue, Pageable pageable);
}
