package com.pavel.jbsrm.deed;

import com.pavel.jbsrm.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "deed")
public class Deed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @NotNull
    @Column(name = "amount")
    private int amount;

    @NotNull
    @Column(name = "price")
    private long price;

    @NotNull
    @Column(name = "create_at")
    private LocalDate createAt = LocalDate.now();

    @NotNull
    @Column(name = "deleted")
    private boolean deleted;
}
