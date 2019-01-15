package com.pavel.jbsrm.deed.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateDeedDto {

    @NotNull
    private long productDetailsId;
    @NotNull
    private int amount;
    @NotNull
    private long price;
}
