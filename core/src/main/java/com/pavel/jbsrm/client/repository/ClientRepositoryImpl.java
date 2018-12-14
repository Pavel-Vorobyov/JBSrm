package com.pavel.jbsrm.client.repository;

import com.pavel.jbsrm.client.Client;
import com.pavel.jbsrm.client.dto.ClientDto;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ClientRepositoryImpl extends QuerydslRepositorySupport implements ClientRepositoryCustom {
    private String queryStart = "select id, title, email, phone, client_role, is_deleted from client where as_tsvector(title, email, phone) @@ to_tsquery('";
    private String queryEnd = "') LIMIT (10);";

    @PersistenceContext
    private EntityManager entityManager;

    public ClientRepositoryImpl() {
        super(ClientDto.class);
    }

    @Override
    public List<Client> findAllByPropsMatch(List<String> searchParams) {
        return searchParams.isEmpty() ? Collections.emptyList()
                : entityManager.createNativeQuery(buildQuery(searchParams), Client.class).getResultList();
    }

    private String buildQuery(List<String> searchParams) {
        return searchParams.stream()
                .map(param -> param + ":*")
                .collect(Collectors.joining(" & ", queryStart, queryEnd));
    }
}
