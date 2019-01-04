package com.pavel.jbsrm.product.details.dto;

import com.pavel.jbsrm.transport.TransportType;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProductDetailsDto {

    private long id;
    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotNull
    private int price;
    @NotNull
    private TransportType requiredType;
}
