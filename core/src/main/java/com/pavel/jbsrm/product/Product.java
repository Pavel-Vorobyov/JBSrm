package com.pavel.jbsrm.product;

import com.pavel.jbsrm.common.hibernate.EnumType;
import com.pavel.jbsrm.common.hibernate.Enumerated;
import com.pavel.jbsrm.deed.Deed;
import com.pavel.jbsrm.transport.TransportType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

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

    @Size(max = 80)
    @NotBlank
    private String title;

    @Enumerated(EnumType.POSTGRES)
    private TransportType requiredType;

    @Enumerated(EnumType.POSTGRES)
    private ProductState productState;

    @Size(max = 200)
    @NotNull
    @Column(name = "amount")
    private int amount;

    @NotNull
    @Column(name = "deleted")
    private boolean deleted;

    @OneToMany(mappedBy = "product")
    private List<Deed> deeds; //todo
}
