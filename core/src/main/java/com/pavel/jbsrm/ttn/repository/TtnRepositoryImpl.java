package com.pavel.jbsrm.ttn.repository;

import com.pavel.jbsrm.ttn.Ttn;
import com.pavel.jbsrm.ttn.TtnState;
import com.pavel.jbsrm.ttn.dto.TtnQuickSearchDto;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TtnRepositoryImpl extends QuerydslRepositorySupport implements TtnRepositoryCustom {
    private String queryStart = "select ttn.id, users.name as driver_name, users.surname as driver_surname, ttn.ttn_state, \n" +
            "\t(select users.name from users where users.id = ttn.created_by) as created_by_name,\n" +
            "\t(select users.surname from users where users.id = ttn.created_by) as created_by_surname,\n" +
            "\tttn.create_at \n" +
            "\tfrom ttn left join users on ttn.driver_id = users.id where as_tsvector(as_text(ttn.ttn_state), \n" +
            "\t(select users.name from users where users.id = ttn.created_by),\n" +
            "\t(select users.surname from users where users.id = ttn.created_by),\n" +
            "\tusers.name, users.surname) @@ to_tsquery('";
    private String queryEnd = "') limit (10);";

    @PersistenceContext
    private EntityManager entityManager;

    public TtnRepositoryImpl() {
        super(Ttn.class);
    }

    @Override
    public List<TtnQuickSearchDto> findAllByPropsMatch(List<String> searchParams) {
        return searchParams.isEmpty() ? Collections.emptyList()
                : mapResultSet(entityManager.createNativeQuery(buildQuery(searchParams)).getResultList());
    }

    private String buildQuery(List<String> searchParams) {
        return searchParams.stream()
                .map(param -> param + ":*")
                .collect(Collectors.joining(" & ", queryStart, queryEnd));
    }

    private List<TtnQuickSearchDto> mapResultSet(List<Object[]> resultList) {
        return resultList.stream()
                .map(o -> TtnQuickSearchDto.builder()
                                .id(Long.valueOf(o[0].toString()))
                                .driverName(o[1].toString())
                                .driverSurname(o[2].toString())
                                .ttnState(TtnState.valueOf(o[3].toString()))
                                .createdByName(o[4].toString())
                                .createdBySurname(o[5].toString())
                                .createdAt(((Date) o[6]).toLocalDate())
                                .build())
                .collect(Collectors.toList());
    }
}
