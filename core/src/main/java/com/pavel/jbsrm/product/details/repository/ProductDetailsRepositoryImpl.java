package com.pavel.jbsrm.product.details.repository;

import com.pavel.jbsrm.product.details.ProductDetails;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDetailsRepositoryImpl extends QuerydslRepositorySupport implements ProductDetailsRepositoryCustom {
    private String queryStart = "select id, title, description, price, requiredtype, deleted from product_details " +
            "where as_tsvector(as_text(id), as_text(requiredtype), title, as_text(deleted)) @@ to_tsquery('";
    private String queryEnd = "') LIMIT (10);";

    @PersistenceContext
    private EntityManager entityManager;

    public ProductDetailsRepositoryImpl() {
        super(ProductDetails.class);
    }

    @Override
    public List<ProductDetails> findAllByPropsMatch(List<String> searchParams) {
        return searchParams.isEmpty() ? Collections.emptyList()
                : entityManager.createNativeQuery(buildQuery(searchParams), ProductDetails.class).getResultList();
    }

    private String buildQuery(List<String> searchParams) {
        return searchParams.stream()
                .map(param -> param + ":*")
                .collect(Collectors.joining(" & ", queryStart, queryEnd));
    }
}
