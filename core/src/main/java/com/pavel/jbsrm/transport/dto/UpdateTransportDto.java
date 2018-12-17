package com.pavel.jbsrm.transport.dto;

import com.pavel.jbsrm.company.Company;
import com.pavel.jbsrm.transport.TransportType;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateTransportDto {

    private TransportType transportType;

    @NotNull
    private int consumption;

    @NotNull
    private Company owner;

    private boolean deleted;
}
