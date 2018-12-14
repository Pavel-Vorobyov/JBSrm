package com.pavel.jbsrm.ttn;

import com.pavel.jbsrm.common.hibernate.EnumType;
import com.pavel.jbsrm.common.hibernate.Enumerated;
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

    @OneToOne(mappedBy = "ttn")
    private Waybill waybill;

    @OneToMany(cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "ttn_products",
        joinColumns = @JoinColumn(name = "ttn_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;

    @Enumerated(EnumType.POSTGRES)
    private TtnState ttnState;

    @NotNull
    @Column(name = "is_approved")
    private boolean isApproved;
}
