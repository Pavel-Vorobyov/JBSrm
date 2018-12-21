package com.pavel.jbsrm.transport.repository;

import com.pavel.jbsrm.transport.Transport;
import com.pavel.jbsrm.transport.dto.TransportDto;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TransportRepositoryImpl extends QuerydslRepositorySupport implements TransportRepositoryCustom {
    private String queryStart = "select id, body_type, consumption, company_id, deleted from transport where as_tsvector(id::TEXT, consumption::TEXT, company_id::TEXT) @@ to_tsquery('";
    private String queryEnd = "') LIMIT (10);";//todo body_type

    @PersistenceContext
    private EntityManager entityManager;

    public TransportRepositoryImpl() {
        super(TransportDto.class);
    }

    @Override
    public List<Transport> findAllByPropsMatch(List<String> searchParams) {
        return searchParams.isEmpty() ? Collections.emptyList()
                : entityManager.createNativeQuery(buildQuery(searchParams), Transport.class).getResultList();
    }

    private String buildQuery(List<String> searchParams) {
        return searchParams.stream()
                .map(param -> param + ":*")
                .collect(Collectors.joining(" & ", queryStart, queryEnd));
    }
}
