package com.pavel.jbsrm.product.repository;

import com.pavel.jbsrm.client.Client;
import com.pavel.jbsrm.client.dto.ClientDto;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;

public class ProductRepositoryImpl extends QuerydslRepositorySupport implements ProductRepositoryCustom {
    private String queryStart = "select id, title, email, phone, client_role, is_active from client where as_tsvector(title, email, phone) @@ to_tsquery('";
    private String queryEnd = "') LIMIT (10);";

    @PersistenceContext
    private EntityManager entityManager;

    public ProductRepositoryImpl() {
        super(ClientDto.class);
    }

    @Override
    public List<Client> findAllByPropsMatch(List<String> searchParams) {
        return searchParams.isEmpty() ? Collections.emptyList()
                : entityManager.createNativeQuery(buildQuery(searchParams), Client.class).getResultList();
    }

    private String buildQuery(List<String> searchParams) {
        StringBuilder resultQuery = new StringBuilder(queryStart);

        for (int i = 0; i < searchParams.size(); i++) {
            resultQuery.append(searchParams.get(i));
            resultQuery.append(":*");
            if (i < searchParams.size() - 1) {
                resultQuery.append(" & ");
            }
        }

        resultQuery.append(queryEnd);
        return resultQuery.toString();
    }
}
