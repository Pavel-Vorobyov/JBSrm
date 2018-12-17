package com.pavel.jbsrm.waybill.repository;

import com.pavel.jbsrm.company.Company;
import com.pavel.jbsrm.waybill.Waybill;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class WaybillRepositoryImpl extends QuerydslRepositorySupport implements WaybillRepositoryCustom {
    private String queryStart = "select id, title, email, phone, client_role, is_deleted from client where as_tsvector(title, email, phone) @@ to_tsquery('";
    private String queryEnd = "') LIMIT (10);"; //todo update select query to Waybill.class

    @PersistenceContext
    private EntityManager entityManager;

    public WaybillRepositoryImpl() {
        super(Waybill.class);
    }

    @Override
    public List<Waybill> findAllByPropsMatch(List<String> searchParams) {
        return searchParams.isEmpty() ? Collections.emptyList()
                : entityManager.createNativeQuery(buildQuery(searchParams), Waybill.class).getResultList();
    }

    private String buildQuery(List<String> searchParams) {
        return searchParams.stream()
                .map(param -> param + ":*")
                .collect(Collectors.joining(" & ", queryStart, queryEnd));
    }
}
