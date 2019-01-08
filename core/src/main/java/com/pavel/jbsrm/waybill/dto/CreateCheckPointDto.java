package com.pavel.jbsrm.waybill.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateCheckPointDto {

    @NotNull
    private String title;
    @NotNull
    private double lat;
    @NotNull
    private double lng;
}
