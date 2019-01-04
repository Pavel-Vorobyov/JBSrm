package com.pavel.jbsrm.transport.dto;

import com.pavel.jbsrm.company.Company;
import com.pavel.jbsrm.transport.TransportType;
import lombok.Data;

@Data
public class TransportDto {

    private long id;
    private String title;
    private int consumption;
    private TransportType bodyType;
    private Company owner;
    private boolean deleted;
}
