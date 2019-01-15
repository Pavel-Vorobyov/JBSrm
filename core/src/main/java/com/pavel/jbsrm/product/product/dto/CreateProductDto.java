package com.pavel.jbsrm.product.product.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateProductDto {

    @NotNull
    private long productDetailsId;
    @NotNull
    private int amount;
}
