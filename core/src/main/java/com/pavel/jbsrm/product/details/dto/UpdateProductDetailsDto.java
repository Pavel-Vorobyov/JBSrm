package com.pavel.jbsrm.product.details.dto;

import com.pavel.jbsrm.transport.TransportType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UpdateProductDetailsDto {

    @NotBlank
    private String title;
    @NotNull
    private String description;
    private int price;
    private TransportType requiredType;
    private boolean isDeleted;
}
