package com.pavel.jbsrm.deed.repository;

import com.pavel.jbsrm.deed.Deed;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DeedRepositoryImpl extends QuerydslRepositorySupport implements DeedRepositoryCustom {
    private String queryStart = "select id, title, email, phone, client_role, is_deleted from client where as_tsvector(title, email, phone) @@ to_tsquery('";
    private String queryEnd = "') LIMIT (10);"; //todo update select query to Deed.class

    @PersistenceContext
    private EntityManager entityManager;

    public DeedRepositoryImpl() {
        super(Deed.class);
    }

    @Override
    public List<Deed> findAllByPropsMatch(List<String> searchParams) {
        return searchParams.isEmpty() ? Collections.emptyList()
                : entityManager.createNativeQuery(buildQuery(searchParams), Deed.class).getResultList();
    }

    private String buildQuery(List<String> searchParams) {
        return searchParams.stream()
                .map(param -> param + ":*")
                .collect(Collectors.joining(" & ", queryStart, queryEnd));
    }
}
