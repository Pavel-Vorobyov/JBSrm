package com.pavel.jbsrm.product.product.repository;

import com.pavel.jbsrm.product.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, QuerydslPredicateExecutor<Product>, ProductRepositoryCustom {
}
