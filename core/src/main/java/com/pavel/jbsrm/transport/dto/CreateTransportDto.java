package com.pavel.jbsrm.transport.dto;

import com.pavel.jbsrm.transport.TransportType;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.security.acl.Owner;

@Data
public class CreateTransportDto {

    private TransportType transportType;

    @NotNull
    private int consumption;

    @NotNull
    private Owner owner;
}
