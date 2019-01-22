package com.pavel.jbsrm.waybill.repository;

import com.pavel.jbsrm.waybill.dto.WaybillQuickSearchDto;

import java.util.List;

public interface WaybillRepositoryCustom {

    List<WaybillQuickSearchDto> findAllByPropsMatch(List<String> props);
}
