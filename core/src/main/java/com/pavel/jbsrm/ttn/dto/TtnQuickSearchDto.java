package com.pavel.jbsrm.ttn.dto;

import com.pavel.jbsrm.ttn.TtnState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class TtnQuickSearchDto {

    private long id;
    private String driverName;
    private String driverSurname;
    private TtnState ttnState;
    private String createdByName;
    private String createdBySurname;
    private LocalDate createdAt;
}
