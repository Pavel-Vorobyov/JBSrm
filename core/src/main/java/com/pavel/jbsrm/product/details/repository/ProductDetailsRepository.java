package com.pavel.jbsrm.product.details.repository;

import com.pavel.jbsrm.product.details.ProductDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDetailsRepository extends JpaRepository<ProductDetails, Long>, QuerydslPredicateExecutor<ProductDetails>, ProductDetailsRepositoryCustom {
    Page<ProductDetails> findByDeleted(boolean deletedValue, Pageable pageable);
}
