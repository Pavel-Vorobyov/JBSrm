package com.pavel.jbsrm.company.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateCompanyDto {

    @NotBlank
    private String title;
    @NotBlank
    private String email;
    @NotBlank
    private String phone;
    @NotBlank
    private String systemAdminEmail;
    @NotBlank
    private String systemAdminPassword;
}
