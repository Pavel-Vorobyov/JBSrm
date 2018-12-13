package com.pavel.jbsrm.product.dto;

import com.pavel.jbsrm.transport.TransportType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateProductDto {

    @NotBlank
    private String title;

    private TransportType requiredType;

    @NotNull
    private int amount;
}
