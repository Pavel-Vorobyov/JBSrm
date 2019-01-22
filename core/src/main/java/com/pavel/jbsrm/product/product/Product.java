package com.pavel.jbsrm.product.product;

import com.pavel.jbsrm.common.hibernate.EnumType;
import com.pavel.jbsrm.common.hibernate.Enumerated;
import com.pavel.jbsrm.product.details.ProductDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "product_details_id")
    private ProductDetails productDetails;

    @Column(name = "amount")
    private int amount;

    @Column(name = "deed")
    private int deed;

    @Enumerated(EnumType.POSTGRES)
    private ProductState productState;

    @Column(name = "deleted")
    private boolean deleted;
}
