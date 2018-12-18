package com.pavel.jbsrm.user.repository;

import com.pavel.jbsrm.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User>,  UserRepositoryCustom {
    Page<User> findByDeleted(boolean deletedValue, Pageable pageable);
}
