package com.pavel.jbsrm.product.details;

import com.pavel.jbsrm.common.hibernate.EnumType;
import com.pavel.jbsrm.common.hibernate.Enumerated;
import com.pavel.jbsrm.transport.TransportType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_details")
public class ProductDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(max = 80)
    @NotBlank
    @Column(name = "title")
    private String title;

    @Size(max = 1000)
    @NotNull
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "price")
    private long price;

    @Enumerated(EnumType.POSTGRES)
    private TransportType requiredType;

    @NotNull
    @Column(name = "deleted")
    private boolean deleted;
}
