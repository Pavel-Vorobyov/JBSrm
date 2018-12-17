package com.pavel.jbsrm.transport.dto;

import com.pavel.jbsrm.company.Company;
import lombok.Data;

@Data
public class TransportDto {

    private long id;
    private int consumption;
    private Company owner;
    private boolean deleted;
}
