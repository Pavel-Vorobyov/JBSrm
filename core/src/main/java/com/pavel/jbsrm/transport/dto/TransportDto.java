package com.pavel.jbsrm.transport.dto;

import com.pavel.jbsrm.company.Company;
import com.pavel.jbsrm.transport.TransportType;
import lombok.Data;

@Data
public class TransportDto {

    private long id;
    private int consumption;
    private TransportType transportType;
    private Company owner;
    private boolean deleted;
}
