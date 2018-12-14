package com.pavel.jbsrm.client.dto;

import com.pavel.jbsrm.client.ClientRole;
import lombok.Data;

@Data
public class ClientDto {
    private long id;
    private String title;
    private String email;
    private String phone;
    private ClientRole clientRole;
    private boolean isDeleted;
}
