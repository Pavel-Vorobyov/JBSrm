package com.pavel.jbsrm.user.dto;

import com.pavel.jbsrm.company.Company;
import com.pavel.jbsrm.user.Passport;
import com.pavel.jbsrm.user.UserGender;
import com.pavel.jbsrm.user.UserRole;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDto {

    private long id;
    private String name;
    private String surname;
    private UserGender userGender;
    private LocalDate birthday;
    private Passport passport;
    private String email;
    private String phone;
    private Company company;
    private UserRole userRole;
    private LocalDate createAt;
    private boolean deleted;
}
