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

    private LocalDate birthday;
    private Long companyId;
    private UserRole userRole;
    private Boolean deleted;
}
