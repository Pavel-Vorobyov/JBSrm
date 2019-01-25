package com.pavel.jbsrm.deed.repository;

import com.pavel.jbsrm.deed.Deed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface DeedRepository extends JpaRepository<Deed, Long>, DeedRepositoryCustom {
    Page<Deed> findByDeleted(boolean deletedValue, Pageable pageable);
}
