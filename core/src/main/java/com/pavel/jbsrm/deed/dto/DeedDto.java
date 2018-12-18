package com.pavel.jbsrm.deed.dto;

import com.pavel.jbsrm.product.Product;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DeedDto {

    private long id;
    private Product product;
    private int amount;
    private long price;
    private LocalDate createAt;
}
