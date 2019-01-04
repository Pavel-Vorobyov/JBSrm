package com.pavel.jbsrm.product.product.dto;

import com.pavel.jbsrm.product.details.ProductDetails;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateProductDto {

    @NotNull
    private ProductDetails productDetails;
    @NotNull
    private int amount;
}
