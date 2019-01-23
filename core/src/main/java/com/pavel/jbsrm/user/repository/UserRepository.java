package com.pavel.jbsrm.user.repository;

import com.pavel.jbsrm.user.User;
import com.pavel.jbsrm.user.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User>, UserRepositoryCustom {
    Optional<User> findByEmail(String email);
    Optional<User> findByCompanyIdAndUserRole(long companyId, UserRole userRole);
}
