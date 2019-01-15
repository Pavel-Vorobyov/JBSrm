package com.pavel.jbsrm.ttn.dto;

import com.pavel.jbsrm.product.product.Product;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CreateTtnDto {

    @NotNull
    private long driverId;

    @NotNull
    private long transportId;

    @NotNull
    private List<Product> products;
}
