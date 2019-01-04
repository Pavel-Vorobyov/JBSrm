package com.pavel.jbsrm.waybill.dto;

import com.pavel.jbsrm.transport.Transport;
import com.pavel.jbsrm.ttn.Ttn;
import com.pavel.jbsrm.waybill.CheckPoint;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class WaybillDto {

    private long id;
    private Ttn ttn;
    private LocalDate createdAt;
    private CheckPoint startPoint;
    private CheckPoint endPoint;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<CheckPoint> checkPoints;
}
