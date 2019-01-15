package com.pavel.jbsrm.user;

import com.pavel.jbsrm.common.hibernate.EnumType;
import com.pavel.jbsrm.common.hibernate.Enumerated;
import com.pavel.jbsrm.company.Company;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Enumerated(EnumType.POSTGRES)
    private UserGender userGender;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "passport_series")
    private String series;

    @Column(name = "passport_issued_by")
    private String issuedBy;

    @Column(name = "phone")
    private String phone;

    @OneToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Enumerated(EnumType.POSTGRES)
    private UserRole userRole;

    @Column(name = "create_at")
    private LocalDate createAt = LocalDate.now();

    @Column(name = "deleted")
    private boolean deleted;
}
