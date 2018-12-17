package com.pavel.jbsrm.transport;

import com.pavel.jbsrm.common.hibernate.EnumType;
import com.pavel.jbsrm.common.hibernate.Enumerated;
import com.pavel.jbsrm.company.Company;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transport")
public class Transport {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Enumerated(EnumType.POSTGRES)
    private TransportType bodyType;

    @Size(max = 200)
    @NotNull
    @Column(name = "consumption")
    private int consumption;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company owner;

    @NotNull
    @Column(name = "deleted")
    private boolean deleted;
}
