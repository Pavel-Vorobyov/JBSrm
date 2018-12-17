package com.pavel.jbsrm.ttn.repository;

import com.pavel.jbsrm.ttn.Ttn;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TtnRepositoryImpl extends QuerydslRepositorySupport implements TtnRepositoryCustom {
    private String queryStart = "select id, title, email, phone, client_role, is_deleted from client where as_tsvector(title, email, phone) @@ to_tsquery('";
    private String queryEnd = "') LIMIT (10);"; //todo update select query to Ttn.class

    @PersistenceContext
    private EntityManager entityManager;

    public TtnRepositoryImpl() {
        super(Ttn.class);
    }

    @Override
    public List<Ttn> findAllByPropsMatch(List<String> searchParams) {
        return searchParams.isEmpty() ? Collections.emptyList()
                : entityManager.createNativeQuery(buildQuery(searchParams), Ttn.class).getResultList();
    }

    private String buildQuery(List<String> searchParams) {
        return searchParams.stream()
                .map(param -> param + ":*")
                .collect(Collectors.joining(" & ", queryStart, queryEnd));
    }
}
