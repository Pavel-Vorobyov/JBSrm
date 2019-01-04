package com.pavel.jbsrm.waybill.dto;

import lombok.Data;
import sun.util.resources.LocaleData;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class CreateCheckPointDto {

    @NotNull
    private String title;
    @NotNull
    private double lat;
    @NotNull
    private double lng;
}
