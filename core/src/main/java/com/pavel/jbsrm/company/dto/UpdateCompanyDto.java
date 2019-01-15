package com.pavel.jbsrm.company.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UpdateCompanyDto {

    @NotBlank
    private String title;
    @NotBlank
    private String email;
    @NotBlank
    private String phone;
    @NotBlank
    private String systemAdminEmail;
    @NotNull
    private boolean deleted;
}
