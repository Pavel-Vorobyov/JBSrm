package com.pavel.jbsrm.product.dto;

import com.pavel.jbsrm.transport.TransportType;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateProductDto {

    @NotBlank
    private String title;
    private TransportType requiredType;
    private boolean isDeleted;
}
