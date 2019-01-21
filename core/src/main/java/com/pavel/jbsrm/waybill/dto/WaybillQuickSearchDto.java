package com.pavel.jbsrm.waybill.dto;

import com.pavel.jbsrm.ttn.TtnState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class WaybillQuickSearchDto {

    private long id;
    private LocalDate createdAt;
    private LocalDate startDate;
    private LocalDate endDate;
    private TtnState ttnState;
}
