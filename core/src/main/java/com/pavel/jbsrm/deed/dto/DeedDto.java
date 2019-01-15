package com.pavel.jbsrm.deed.dto;

import com.pavel.jbsrm.product.product.Product;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DeedDto {

    private long id;
    private Product product;
    private long price;
    private LocalDate createAt;
}
