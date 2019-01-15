package com.pavel.jbsrm.waybill.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pavel.jbsrm.ttn.Ttn;
import com.pavel.jbsrm.waybill.CheckPoint;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class WaybillDto {

    private long id;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Ttn ttn;

    private LocalDate createdAt;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<CheckPoint> checkPoints;
}
