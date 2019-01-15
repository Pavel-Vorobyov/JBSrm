package com.pavel.jbsrm.waybill;

import com.pavel.jbsrm.ttn.TtnState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WaybillFilter {

    TtnState ttnState;
    Boolean deleted;
}
