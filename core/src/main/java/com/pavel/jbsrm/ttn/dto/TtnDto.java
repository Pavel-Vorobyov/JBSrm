package com.pavel.jbsrm.ttn.dto;

import com.pavel.jbsrm.ttn.TtnState;
import com.pavel.jbsrm.user.User;
import com.pavel.jbsrm.waybill.Waybill;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TtnDto {

    private long id;
    private User driver;
    private Waybill waybill;
    private TtnState ttnState;
    private User createdBy;
    private LocalDate createAt;
    private boolean deleted;
}
