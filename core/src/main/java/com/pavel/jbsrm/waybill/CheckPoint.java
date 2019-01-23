package com.pavel.jbsrm.waybill;

import com.pavel.jbsrm.common.hibernate.EnumType;
import com.pavel.jbsrm.common.hibernate.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

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
    @Column(name = "title")
    private String title;

    @Column(name = "lat")
    private double lat;

    @Column(name = "lng")
    private double lng;

    @Enumerated(EnumType.POSTGRES)
    private CheckPointStatus checkPointStatus;

    @Column(name = "deleted")
    private boolean deleted = false;
}
