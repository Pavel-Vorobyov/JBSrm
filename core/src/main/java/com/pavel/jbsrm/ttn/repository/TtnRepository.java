package com.pavel.jbsrm.ttn.repository;

import com.pavel.jbsrm.ttn.Ttn;
import com.pavel.jbsrm.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

public interface TtnRepository extends JpaRepository<Ttn, Long>, QuerydslPredicateExecutor<Ttn>, TtnRepositoryCustom {
    Page<Ttn> findByDeleted(boolean deletedValue, Pageable pageable);
}
