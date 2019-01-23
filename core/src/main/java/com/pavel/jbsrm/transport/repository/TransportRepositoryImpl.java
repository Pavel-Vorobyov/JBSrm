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
    private String queryStart = "select t.id, t.title, t.body_type, t.consumption, t.company_id, t.transport_state, t.deleted \n" +
            "\tfrom transport as t\n" +
            "\twhere as_tsvector(as_text(t.id), t.title, as_text(t.body_type), as_text(t.consumption), as_text(t.company_id), as_text(t.transport_state)) @@ to_tsquery('";
    private String queryEnd = "') LIMIT (10);";

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
