package com.pavel.jbsrm.transport;

import com.pavel.jbsrm.client.Client;
import com.pavel.jbsrm.common.hibernate.EnumType;
import com.pavel.jbsrm.common.hibernate.Enumerated;
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
    @JoinColumn(name = "client_id")
    private Client owner;

    @NotNull
    @Column(name = "is_deleted")
    private boolean isDeleted;
}
