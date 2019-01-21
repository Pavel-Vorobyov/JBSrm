package com.pavel.jbsrm.waybill.repository;

import com.pavel.jbsrm.ttn.TtnState;
import com.pavel.jbsrm.waybill.Waybill;
import com.pavel.jbsrm.waybill.dto.WaybillQuickSearchDto;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class WaybillRepositoryImpl extends QuerydslRepositorySupport implements WaybillRepositoryCustom {
    private String queryStart = "select w.id, w.created_at, w.start_date, w.end_date, t.ttnstate " +
            "\tfrom waybill as w \n" +
            "\tleft join ttn as t on t.id = w.ttn_id\n" +
            "\twhere as_tsvector(as_text(w.id)) @@ to_tsquery('";
    private String queryEnd = "') LIMIT (10);";

    @PersistenceContext
    private EntityManager entityManager;

    public WaybillRepositoryImpl() {
        super(Waybill.class);
    }

    @Override
    public List<WaybillQuickSearchDto> findAllByPropsMatch(List<String> searchParams) {
        return searchParams.isEmpty() ? Collections.emptyList()
                : mapResultSet(entityManager.createNativeQuery(buildQuery(searchParams)).getResultList());
    }

    private String buildQuery(List<String> searchParams) {
        return searchParams.stream()
                .map(param -> param + ":*")
                .collect(Collectors.joining(" & ", queryStart, queryEnd));
    }

    private List<WaybillQuickSearchDto> mapResultSet(List<Object[]> resultSet) {
        return resultSet.stream()
                .map(result -> WaybillQuickSearchDto.builder()
                        .id(Long.valueOf(result[0].toString()))
                        .createdAt(LocalDate.parse(result[1].toString()))
                        .startDate(LocalDate.parse(result[2].toString()))
                        .endDate(LocalDate.parse(result[3].toString()))
                        .ttnState(TtnState.valueOf(result[4].toString()))
                        .build()
                ).collect(Collectors.toList());
    }
}
