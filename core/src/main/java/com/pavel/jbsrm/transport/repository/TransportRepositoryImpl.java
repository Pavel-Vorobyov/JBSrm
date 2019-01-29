package com.pavel.jbsrm.transport.repository;

import com.pavel.jbsrm.common.postgres.FtsExpression;
import com.pavel.jbsrm.transport.QTransport;
import com.pavel.jbsrm.transport.Transport;
import com.pavel.jbsrm.transport.dto.TransportDto;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.beans.Expression;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TransportRepositoryImpl extends QuerydslRepositorySupport implements TransportRepositoryCustom {
    private String queryStart = "select t.id, t.title, t.bodytype, t.consumption, t.company_id, t.transportstate, t.deleted \n" +
            "\tfrom transport as t\n" +
            "\twhere as_tsvector() @@ to_tsquery('";
    private String queryEnd = "') LIMIT (10);";

    private String searchingTypes = "as_text(t.id), t.title, as_text(t.bodytype), as_text(t.consumption), as_text(t.company_id), as_text(t.transportstate)";

    @PersistenceContext
    private EntityManager entityManager;

    public TransportRepositoryImpl() {
        super(TransportDto.class);
    }

    @Override
    public List<Transport> findAllByPropsMatch(List<String> searchParams) {
        JPAQuery<Transport> query = new JPAQuery<>(entityManager);

        query.from(QTransport.transport);
        query.where(FtsExpression.ftr(buildSearchingParams(searchParams), QTransport.transport.title));
        query.limit(10);

        return searchParams.isEmpty() ? Collections.emptyList()
                : query.fetch();
    }

    private String buildSearchingParams(List<String> searchParams) {
        return searchParams.stream()
                .map(param -> param + ":*")
                .collect(Collectors.joining(" & "));
    }
}
