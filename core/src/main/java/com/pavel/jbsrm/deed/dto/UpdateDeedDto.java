package com.pavel.jbsrm.deed.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateDeedDto {

    @NotNull
    private long productId;

    @NotNull
    private int amount;

    @NotNull
    private long price;
}
