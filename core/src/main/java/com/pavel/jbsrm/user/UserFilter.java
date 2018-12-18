package com.pavel.jbsrm.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserFilter {

    private LocalDate age;
    private Long companyId;
    private UserRole userRole;
    private Boolean deleted;

    /*
        {
            "age": "2018-12-17",
            "companyId": 2,
            "userRole": "ADMIN",
            "deleted": false
        }
     */
}
