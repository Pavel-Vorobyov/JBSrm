package com.pavel.jbsrm.transport.dto;

import com.pavel.jbsrm.company.Company;
import com.pavel.jbsrm.transport.TransportType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateTransportDto {

    @NotBlank
    private String title;
    private TransportType bodyType;
    @NotNull
    private int consumption;
    @NotNull
    private Company owner;
}
