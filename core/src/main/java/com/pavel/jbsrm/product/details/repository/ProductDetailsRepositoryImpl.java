package com.pavel.jbsrm.product.details.repository;

import com.pavel.jbsrm.product.details.ProductDetails;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDetailsRepositoryImpl extends QuerydslRepositorySupport implements ProductDetailsRepositoryCustom {
    private String queryStart = "select pd.id, pd.title, pd.description, pd.price, pd.requiredtype, pd.deleted \n" +
            "\tfrom product_details as pd\n" +
            "\twhere as_tsvector(as_text(pd.id), as_text(pd.requiredtype), pd.title, as_text(pd.deleted), as_text(pd.price)) @@ to_tsquery('";
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
