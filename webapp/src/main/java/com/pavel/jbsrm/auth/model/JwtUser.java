package com.pavel.jbsrm.auth.model;

import com.pavel.jbsrm.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtUser {

    private long id;
    private String name;
    private String token;
    private UserRole userRole;
}
