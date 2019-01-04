package com.pavel.jbsrm.waybill.dto;

import com.pavel.jbsrm.ttn.Ttn;
import com.pavel.jbsrm.waybill.CheckPoint;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
public class CreateWaybillDto {

    @NotNull
    private Ttn ttn;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    private List<CreateCheckPointDto> checkPoints;
}
