package com.pavel.jbsrm.product.product.dto;

import com.pavel.jbsrm.product.details.ProductDetails;
import com.pavel.jbsrm.product.product.ProductState;
import lombok.Data;

@Data
public class ProductDto {

    private long id;
    private ProductDetails productDetails;
    private int amount;
    private ProductState productState;
    private boolean deleted;
}
