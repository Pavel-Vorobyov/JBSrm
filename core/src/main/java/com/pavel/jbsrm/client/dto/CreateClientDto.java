package com.pavel.jbsrm.client.dto;

import com.pavel.jbsrm.client.ClientRole;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateClientDto {

    @NotEmpty
    private String title;

    @NotEmpty
    private String email;

    @NotEmpty
    private String phone;

    @NotEmpty
    private ClientRole clientRole;
}
