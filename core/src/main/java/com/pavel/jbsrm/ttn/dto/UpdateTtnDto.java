package com.pavel.jbsrm.ttn.dto;

import com.pavel.jbsrm.product.product.Product;
import com.pavel.jbsrm.ttn.TtnState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTtnDto {

    @NotNull
    private long driverId;
    @NotNull
    private long transportId;
    @NotNull
    private List<Product> products;
    @NotNull
    private TtnState ttnState;
    @NotNull
    private boolean deleted;
}
