package com.pavel.jbsrm.product.repository;

import com.pavel.jbsrm.product.Product;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ProductRepositoryImpl extends QuerydslRepositorySupport implements ProductRepositoryCustom {
    private String queryStart = "select id, title, required_type, product_state, amount, is_deleted from product " +
            "where as_tsvector(id, required_type, product_state, title, amount) @@ to_tsquery('";
    private String queryEnd = "') LIMIT (10);";

    @PersistenceContext
    private EntityManager entityManager;

    public ProductRepositoryImpl() {
        super(Product.class);
    }

    @Override
    public List<Product> findAllByPropsMatch(List<String> searchParams) {
        return searchParams.isEmpty() ? Collections.emptyList()
                : entityManager.createNativeQuery(buildQuery(searchParams), Product.class).getResultList();
    }

    private String buildQuery(List<String> searchParams) {
        return searchParams.stream()
                .map(param -> param + ":*")
                .collect(Collectors.joining(" & ", queryStart, queryEnd));
    }
}
