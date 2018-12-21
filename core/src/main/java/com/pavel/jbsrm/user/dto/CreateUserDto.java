package com.pavel.jbsrm.user.dto;

import com.pavel.jbsrm.company.Company;
import com.pavel.jbsrm.user.Passport;
import com.pavel.jbsrm.user.UserGender;
import com.pavel.jbsrm.user.UserRole;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class CreateUserDto {

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    private UserGender userGender;

    @NotNull
    private LocalDate birthday;

    private Passport passport;

    @NotBlank
    private String email;

    @NotBlank
    private String phone;

    @NotNull
    private Company company;

    private UserRole userRole;

}
