package com.pavel.jbsrm.waybill;

import com.pavel.jbsrm.transport.Transport;
import com.pavel.jbsrm.ttn.Ttn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "waybill")
public class Waybill {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ttn_id")
    private Ttn ttn;

    @OneToOne
    @JoinColumn(name = "transport_id")
    private Transport transport;

    @Column(name = "created_at")
    private LocalDate createdAt = LocalDate.now();

    @OneToOne
    @JoinColumn(name = "start_point_id")
    private CheckPoint startPoint;

    @OneToOne
    @JoinColumn(name = "end_point_id")
    private CheckPoint endPoint;

    @NotNull
    @Column(name = "start_date")
    private LocalDate startDate;

    @NotNull
    @Column(name = "end_date")
    private LocalDate endDate;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "check_point")
    private List<CheckPoint> checkPoints = new ArrayList<>();

    @NotNull
    @Column(name = "deleted")
    private boolean deleted;
}
