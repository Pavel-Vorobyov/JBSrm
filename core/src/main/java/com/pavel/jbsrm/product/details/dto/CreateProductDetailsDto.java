package com.pavel.jbsrm.product.details.dto;

import com.pavel.jbsrm.transport.TransportType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateProductDetailsDto {

    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotNull
    private int price;
    private TransportType requiredType;
}
