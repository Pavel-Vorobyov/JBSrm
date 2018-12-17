package com.pavel.jbsrm.company.repository;

import com.pavel.jbsrm.company.Company;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CompanyRepositoryImpl extends QuerydslRepositorySupport implements CompanyRepositoryCustom {
    private String queryStart = "select id, title, email, phone, client_role, is_deleted from client where as_tsvector(title, email, phone) @@ to_tsquery('";
    private String queryEnd = "') LIMIT (10);"; //todo update select query to Company.class

    @PersistenceContext
    private EntityManager entityManager;

    public CompanyRepositoryImpl() {
        super(Company.class);
    }

    @Override
    public List<Company> findAllByPropsMatch(List<String> searchParams) {
        return searchParams.isEmpty() ? Collections.emptyList()
                : entityManager.createNativeQuery(buildQuery(searchParams), Company.class).getResultList();
    }

    private String buildQuery(List<String> searchParams) {
        return searchParams.stream()
                .map(param -> param + ":*")
                .collect(Collectors.joining(" & ", queryStart, queryEnd));
    }
}