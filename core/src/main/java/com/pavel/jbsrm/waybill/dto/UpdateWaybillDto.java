package com.pavel.jbsrm.waybill.dto;

import com.pavel.jbsrm.ttn.TtnState;
import com.pavel.jbsrm.waybill.CheckPoint;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
public class UpdateWaybillDto {

    @NotNull
    private long ttnId;
    private TtnState ttnState;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    private List<CheckPoint> checkPoints;
}
