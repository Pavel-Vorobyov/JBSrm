package com.pavel.jbsrm.auth.model;

import com.pavel.jbsrm.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthUserDetails {

    private long id;
    private String token;
    private UserRole userRole;
}
