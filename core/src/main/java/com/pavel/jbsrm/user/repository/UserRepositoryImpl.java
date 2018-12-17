package com.pavel.jbsrm.user.repository;

import com.pavel.jbsrm.client.dto.ClientDto;
import com.pavel.jbsrm.user.User;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UserRepositoryImpl extends QuerydslRepositorySupport implements UserRepositoryCustom {
    private String queryStart = "select id, title, email, phone, client_role, is_deleted from client where as_tsvector(title, email, phone) @@ to_tsquery('";
    private String queryEnd = "') LIMIT (10);"; //todo replace query

    @PersistenceContext
    private EntityManager entityManager;

    public UserRepositoryImpl() {
        super(ClientDto.class);
    }

    @Override
    public List<User> findAllByPropsMatch(List<String> searchParams) {
        return searchParams.isEmpty() ? Collections.emptyList()
                : entityManager.createNativeQuery(buildQuery(searchParams), User.class).getResultList();
    }

    private String buildQuery(List<String> searchParams) {
        return searchParams.stream()
                .map(param -> param + ":*")
                .collect(Collectors.joining(" & ", queryStart, queryEnd));
    }
}
