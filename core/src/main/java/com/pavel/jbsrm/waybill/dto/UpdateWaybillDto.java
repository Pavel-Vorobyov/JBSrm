package com.pavel.jbsrm.waybill.dto;

import com.pavel.jbsrm.transport.Transport;
import com.pavel.jbsrm.ttn.Ttn;
import com.pavel.jbsrm.waybill.CheckPoint;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
public class UpdateWaybillDto {

    @NotNull
    private Ttn ttn;

    @NotNull
    private CheckPoint startPoint;

    @NotNull
    private CheckPoint endPoint;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    private List<CheckPoint> checkPoints;
}
