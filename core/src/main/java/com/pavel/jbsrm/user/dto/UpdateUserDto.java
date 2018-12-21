package com.pavel.jbsrm.user.dto;

import com.pavel.jbsrm.company.Company;
import com.pavel.jbsrm.user.Passport;
import com.pavel.jbsrm.user.UserGender;
import com.pavel.jbsrm.user.UserRole;
import lombok.Data;
import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UpdateUserDto {

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    private UserGender userGender;

    @NotNull
    private LocalDate birthday;

    @NotBlank
    private String email;

    @NotBlank
    private String phone;

    @NotNull
    private long companyId;

    private UserRole userRole;
}
