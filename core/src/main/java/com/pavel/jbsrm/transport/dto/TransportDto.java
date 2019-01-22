package com.pavel.jbsrm.transport.dto;

import com.pavel.jbsrm.company.Company;
import com.pavel.jbsrm.transport.TransportState;
import com.pavel.jbsrm.transport.TransportType;
import lombok.Data;

@Data
public class TransportDto {

    private long id;
    private String title;
    private Company owner;
    private int consumption;
    private TransportType bodyType;
    private TransportState transportState;
    private boolean deleted;
}
