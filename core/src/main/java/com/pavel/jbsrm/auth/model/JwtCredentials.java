package com.pavel.jbsrm.auth.model;

import lombok.Data;

@Data
public class JwtCredentials {

    private String email;
    private String password;
}
