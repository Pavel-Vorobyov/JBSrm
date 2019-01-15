package com.pavel.jbsrm.transport.dto;

import com.pavel.jbsrm.transport.TransportType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UpdateTransportDto {

    @NotBlank
    private String title;
    private TransportType bodyType;
    @NotNull
    private int consumption;
    @NotNull
    private long ownerId;
    private boolean deleted;
}
