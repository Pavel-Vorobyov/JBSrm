package com.pavel.jbsrm.transport.dto;

import com.pavel.jbsrm.client.Client;
import com.pavel.jbsrm.transport.TransportType;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateTransportDto {

    private TransportType transportType;

    @NotNull
    private int consumption;

    @NotNull
    private Client owner;

    private boolean deleted;
}
