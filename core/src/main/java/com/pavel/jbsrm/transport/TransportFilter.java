package com.pavel.jbsrm.transport;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransportFilter {

    private Boolean deleted;
    private TransportState transportState;
    private TransportType bodyType;
}
