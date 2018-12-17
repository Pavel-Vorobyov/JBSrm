package com.pavel.jbsrm.ttn.dto;

import com.pavel.jbsrm.product.Product;
import com.pavel.jbsrm.ttn.TtnState;
import lombok.Data;

import java.util.List;

@Data
public class CreateTtnDto {

    private List<Product> products;
}
