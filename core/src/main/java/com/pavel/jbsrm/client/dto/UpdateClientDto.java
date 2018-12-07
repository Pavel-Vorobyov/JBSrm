package com.pavel.jbsrm.client.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UpdateClientDto {

    @NotEmpty
    private String title;

    @NotEmpty
    private String email;

    @NotEmpty
    private String phone;

    @NotEmpty
    private boolean isActive;
}
