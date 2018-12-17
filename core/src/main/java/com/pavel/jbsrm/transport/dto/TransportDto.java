package com.pavel.jbsrm.transport.dto;

import com.pavel.jbsrm.client.Client;
import lombok.Data;

@Data
public class TransportDto {

    private long id;
    private int consumption;
    private Client owner;
    private boolean deleted;
}
