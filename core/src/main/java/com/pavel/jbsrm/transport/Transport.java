package com.pavel.jbsrm.transport;

import com.pavel.jbsrm.client.Client;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "body_type")
    private TransportType bodyType;

    @Size(max = 200)
    @NotNull
    @Column(name = "consumption")
    private int consumption;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
}
