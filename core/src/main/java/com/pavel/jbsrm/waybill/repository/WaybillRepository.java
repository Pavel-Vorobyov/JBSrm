package com.pavel.jbsrm.waybill.repository;

import com.pavel.jbsrm.waybill.Waybill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WaybillRepository extends JpaRepository<Waybill, Long>, WaybillRepositoryCustom {
    Page<Waybill> findByDeleted(boolean deletedValue, Pageable pageable);
}