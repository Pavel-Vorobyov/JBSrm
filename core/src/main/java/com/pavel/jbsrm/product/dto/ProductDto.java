package com.pavel.jbsrm.product.dto;

import com.pavel.jbsrm.product.ProductState;
import com.pavel.jbsrm.transport.TransportType;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProductDto {

    @NotNull
    private String title;

    private TransportType requiredType;

    private ProductState productState;

    @NotNull
    private int amount;
}
