package com.pavel.jbsrm.ttn.dto;

import com.pavel.jbsrm.product.product.Product;
import com.pavel.jbsrm.transport.Transport;
import com.pavel.jbsrm.ttn.TtnState;
import com.pavel.jbsrm.user.User;
import com.pavel.jbsrm.waybill.Waybill;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UpdateTtnDto {

    @NotNull
    private User driver;
    @NotNull
    private Transport transport;
    @NotNull
    private List<Product> products;
    @NotNull
    private TtnState ttnState;
    @NotNull
    private boolean deleted;
}
