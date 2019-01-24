package com.pavel.jbsrm.product.details;

import com.pavel.jbsrm.transport.TransportType;
import lombok.Data;

@Data
public class ProductDetailsFilter {

    private String title;
    private Boolean deleted;
    private TransportType requiredType;
}
