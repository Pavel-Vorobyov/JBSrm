package com.pavel.jbsrm.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserCredDto {

    @NotBlank
    private String oldPassword;
    @NotBlank
    private String newPassword;
}
