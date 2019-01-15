package com.pavel.jbsrm.ttn;

import com.pavel.jbsrm.common.hibernate.EnumType;
import com.pavel.jbsrm.common.hibernate.Enumerated;
import com.pavel.jbsrm.product.product.Product;
import com.pavel.jbsrm.transport.Transport;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "create_at")
    private LocalDate createAt = LocalDate.now();

    @OneToOne
    @JoinColumn(name = "driver_id")
    private User driver;

    @OneToOne
    @JoinColumn(name = "transport_id")
    private Transport transport;

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

    @OneToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @Column(name = "deleted")
    private boolean deleted;
}
