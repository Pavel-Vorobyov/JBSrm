package com.pavel.jbsrm.transport;

import com.pavel.jbsrm.transport.TransportState;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransportFilter {

    private Boolean deleted;
    private TransportState transportState;
}
