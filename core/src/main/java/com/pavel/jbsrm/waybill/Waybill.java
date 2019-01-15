package com.pavel.jbsrm.waybill;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pavel.jbsrm.ttn.Ttn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ttn_id")
    private Ttn ttn;

    @NotNull
    @Column(name = "created_at")
    private LocalDate createdAt = LocalDate.now();

    @NotNull
    @Column(name = "start_date")
    private LocalDate startDate;

    @NotNull
    @Column(name = "end_date")
    private LocalDate endDate;

    @OneToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "waybill_checkpoints",
            joinColumns = @JoinColumn(name = "waybill_id"),
            inverseJoinColumns = @JoinColumn(name = "checkpoint_id"))
    @BatchSize(size = 50)
    private List<CheckPoint> checkPoints = new ArrayList<>();

    @NotNull
    @Column(name = "deleted")
    private boolean deleted;
}
