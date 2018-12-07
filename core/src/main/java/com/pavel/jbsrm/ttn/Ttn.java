package com.pavel.jbsrm.ttn;

import com.pavel.jbsrm.product.Product;
import com.pavel.jbsrm.waybill.Waybill;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ttn")
public class Ttn {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "waybill_id")
    private Waybill waybill;

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Product> products;

    @NotNull
    @Column(name = "is_approved")
    private boolean isApproved;
}
