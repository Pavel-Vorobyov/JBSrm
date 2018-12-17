package com.pavel.jbsrm.ttn;

import com.pavel.jbsrm.common.hibernate.EnumType;
import com.pavel.jbsrm.common.hibernate.Enumerated;
import com.pavel.jbsrm.product.Product;
import com.pavel.jbsrm.user.User;
import com.pavel.jbsrm.waybill.Waybill;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
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

    @NotNull
    @Column(name = "create_at")
    private LocalDate createAt;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User driver;

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
    @Column(name = "deleted")
    private boolean deleted;
}
