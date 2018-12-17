package com.pavel.jbsrm.deed.dto;

import com.pavel.jbsrm.product.Product;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateDeedDto {

    @NotNull
    private Product product;

    @NotNull
    private int amount;

    @NotNull
    private long price;
}
