package com.pavel.jbsrm.product.dto;

import com.pavel.jbsrm.product.ProductState;
import com.pavel.jbsrm.transport.TransportType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UpdateProductDto {

    @NotBlank
    private String title;

    private TransportType requiredType;

    private ProductState productState;

    @NotNull
    private int amount;

    private boolean isDeleted;
}
