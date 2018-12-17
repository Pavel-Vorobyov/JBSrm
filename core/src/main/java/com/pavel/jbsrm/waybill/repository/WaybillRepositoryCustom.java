package com.pavel.jbsrm.waybill.repository;

import com.pavel.jbsrm.waybill.Waybill;

import java.util.List;

public interface WaybillRepositoryCustom {

    List<Waybill> findAllByPropsMatch(List<String> props);
}
