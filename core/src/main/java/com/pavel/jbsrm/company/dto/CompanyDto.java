package com.pavel.jbsrm.company.dto;

import lombok.Data;

@Data
public class CompanyDto {

    private long id;
    private String title;
    private String email;
    private String phone;
    private long adminId;
    private String systemAdminEmail;
}
