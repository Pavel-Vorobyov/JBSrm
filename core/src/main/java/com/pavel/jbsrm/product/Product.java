package com.pavel.jbsrm.product;

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
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Size(max = 80)
    @NotBlank
    private String title;

    @Enumerated(EnumType.STRING)
    private TransportType requiredType;

    @Size(max = 200)
    @NotNull
    @Column(name = "amount")
    private int amount;
}