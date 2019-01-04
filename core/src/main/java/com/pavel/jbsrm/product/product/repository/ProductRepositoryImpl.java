package com.pavel.jbsrm.product.product.repository;

import com.pavel.jbsrm.product.product.dto.ProductQuickSearchDto;
import com.pavel.jbsrm.transport.TransportType;
import com.pavel.jbsrm.user.User;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ProductRepositoryImpl extends QuerydslRepositorySupport implements ProductRepositoryCustom {
    private final String queryStart = "select p.id, p.amount, pd.title, pd.required_type, pd.price " +
            "from product as p left join product_details as pd on p.product_details_id = pd.id \n" +
            "\twhere as_tsvector(pd.title, as_text(pd.price), as_text(pd.required_type)) @@ to_tsquery('";
    private final String queryEnd = "') LIMIT (10);"; //todo replace query

    @PersistenceContext
    private EntityManager entityManager;

    public ProductRepositoryImpl() {
        super(ProductQuickSearchDto.class);
    }

    @Override
    public List<ProductQuickSearchDto> findAllByPropsMatch(List<String> searchParams) {
        return searchParams.isEmpty() ? Collections.emptyList()
                : mapResultSet(entityManager.createNativeQuery(buildQuery(searchParams)).getResultList());
    }

    private String buildQuery(List<String> searchParams) {
        return searchParams.stream()
                .map(param -> param + ":*")
                .collect(Collectors.joining(" & ", queryStart, queryEnd));
    }

    private List<ProductQuickSearchDto> mapResultSet(List<Object[]> resultSet) {
        return resultSet.stream()
                .map(result -> ProductQuickSearchDto.builder()
                                .id(Long.valueOf(result[0].toString()))
                                .amount(Integer.valueOf(result[1].toString()))
                                .title(result[2].toString())
                                .requiredType(TransportType.valueOf(result[3].toString()))
                                .price(Integer.valueOf(result[4].toString()))
                                .build()
                ).collect(Collectors.toList());
    }
}
