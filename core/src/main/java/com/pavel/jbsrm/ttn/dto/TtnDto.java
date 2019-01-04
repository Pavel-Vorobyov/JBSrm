package com.pavel.jbsrm.ttn.dto;

import com.pavel.jbsrm.product.product.Product;
import com.pavel.jbsrm.transport.Transport;
import com.pavel.jbsrm.ttn.TtnState;
import com.pavel.jbsrm.user.User;
import com.pavel.jbsrm.waybill.Waybill;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class TtnDto {

    private long id;
    private User driver;
    private Waybill waybill;
    private Transport transport;
    private TtnState ttnState;
    private List<Product> products;
    private User createdBy;
    private LocalDate createAt;
    private boolean deleted;
}
