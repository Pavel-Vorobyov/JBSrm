package com.pavel.jbsrm.user.dto;

import com.pavel.jbsrm.company.Company;
import com.pavel.jbsrm.user.UserGender;
import com.pavel.jbsrm.user.UserRole;
import lombok.Data;
import sun.util.resources.LocaleData;

@Data
public class UserDto {

    private long id;
    private String name;
    private String surname;
    private UserGender userGender;
    private LocaleData age;
    private String email;
    private String phone;
    private Company company;
    private UserRole userRole;
    private LocaleData createAt;
}
