package com.pavel.jbsrm.ttn.repository;

import com.pavel.jbsrm.ttn.dto.TtnQuickSearchDto;

import java.util.List;

public interface TtnRepositoryCustom {

    List<TtnQuickSearchDto> findAllByPropsMatch(List<String> props);
}
