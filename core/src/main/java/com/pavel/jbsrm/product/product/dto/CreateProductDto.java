package com.pavel.jbsrm.product.product.dto;

import com.pavel.jbsrm.product.product.ProductState;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateProductDto {

    @NotNull
    private long productDetailsId;
    @NotNull
    private int amount;
    private ProductState productState;
    private int deed;
}
