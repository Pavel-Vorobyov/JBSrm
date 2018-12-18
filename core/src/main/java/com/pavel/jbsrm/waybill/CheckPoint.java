package com.pavel.jbsrm.waybill;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "check_point")
public class CheckPoint {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "waybill_id")
    private Waybill waybill;

    @Size(max = 255)
    @NotBlank
    @Column(name = "title")
    private String title;

    @Column(name = "created_at")
    private LocalDate createdAt;
}
