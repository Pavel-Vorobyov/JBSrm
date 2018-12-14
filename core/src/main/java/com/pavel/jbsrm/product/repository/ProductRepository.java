package com.pavel.jbsrm.product.repository;

import com.pavel.jbsrm.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long>, ProductRepositoryCustom {
    Page<Product> findByIsDeleted(boolean isDeletedValue, Pageable pageable);
}
