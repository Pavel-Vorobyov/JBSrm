package com.pavel.jbsrm.common.auth.model;

import lombok.Data;

@Data
public class JwtCredentials {

    private String email;
    private String password;
}
