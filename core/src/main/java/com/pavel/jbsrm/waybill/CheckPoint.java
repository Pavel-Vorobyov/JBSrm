package com.pavel.jbsrm.waybill;

import com.pavel.jbsrm.common.hibernate.EnumType;
import com.pavel.jbsrm.common.hibernate.Enumerated;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(max = 255)
    @NotBlank
    @Column(name = "title")
    private String title;

    @NotNull
    @Column(name = "lat")
    private double lat;

    @NotNull
    @Column(name = "lng")
    private double lng;

    @Enumerated(EnumType.POSTGRES)
    private CheckPointStatus checkPointStatus;

    @Column(name = "created_at")
    private LocalDate createdAt = LocalDate.now();

    @NotNull
    @Column(name = "deleted")
    private boolean deleted = false;
}
