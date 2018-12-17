package com.pavel.jbsrm.ttn.dto;

import com.pavel.jbsrm.user.User;
import com.pavel.jbsrm.waybill.Waybill;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateTtnDto {

    @NotNull
    private User driver;

    @NotNull
    private Waybill waybill;
}
