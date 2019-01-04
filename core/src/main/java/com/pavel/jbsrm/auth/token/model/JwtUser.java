package com.pavel.jbsrm.auth.token.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class JwtUser {

    private long id;
    private String name;
    private String role;
}
