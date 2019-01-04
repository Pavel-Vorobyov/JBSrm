package com.pavel.jbsrm.waybill.repository;

import com.pavel.jbsrm.waybill.CheckPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckPointRepository extends JpaRepository<CheckPoint, Long> {
}
