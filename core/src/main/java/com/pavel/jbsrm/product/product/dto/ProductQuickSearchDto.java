package com.pavel.jbsrm.product.product.dto;

import com.pavel.jbsrm.transport.TransportType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProductQuickSearchDto {

    private long id;
    private int amount;
    private String title;
    private TransportType requiredType;
    private int price;
}
