package com.pavel.jbsrm.auth.model;

import com.pavel.jbsrm.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AuthUser {

    private String token;
    private String tokenType;
    private UserRole userRole;
}
