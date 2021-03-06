package com.pavel.jbsrm.product.product.dto;

import com.pavel.jbsrm.product.product.ProductState;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateProductDto {

    @NotNull
    private long productDetailsId;
    @NotNull
    private ProductState productState;
    @NotNull
    private int amount;
    private int deed;
    @NotNull
    private boolean deleted;
}
