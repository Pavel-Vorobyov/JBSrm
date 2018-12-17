package com.pavel.jbsrm.user.dto;

import com.pavel.jbsrm.company.Company;
import com.pavel.jbsrm.user.UserGender;
import com.pavel.jbsrm.user.UserRole;
import lombok.Data;
import sun.util.resources.LocaleData;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateUserDto {

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    private UserGender userGender;

    @NotNull
    private LocaleData age;

    @NotBlank
    private String email;

    @NotBlank
    private String phone;

    @NotNull
    private Company company;

    private UserRole userRole;

}
