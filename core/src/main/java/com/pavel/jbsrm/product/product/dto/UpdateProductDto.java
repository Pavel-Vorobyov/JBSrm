package com.pavel.jbsrm.product.product.dto;

import com.pavel.jbsrm.product.details.ProductDetails;
import com.pavel.jbsrm.product.product.ProductState;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateProductDto {

    @NotNull
    private ProductDetails productDetails;
    @NotNull
    private ProductState productState;
    @NotNull
    private int amount;
    @NotNull
    private boolean deleted;
}
