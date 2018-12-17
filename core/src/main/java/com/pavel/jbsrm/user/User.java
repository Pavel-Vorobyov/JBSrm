package com.pavel.jbsrm.user;

import com.pavel.jbsrm.common.hibernate.EnumType;
import com.pavel.jbsrm.common.hibernate.Enumerated;
import com.pavel.jbsrm.company.Company;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotBlank
    @Column(name = "surname")
    private String surname;

    @Enumerated(EnumType.POSTGRES)
    private UserGender userGender;

    @NotNull
    @Column(name = "age")
    private LocalDate age;

    @NotBlank
    @Column(name = "email")
    private String email;

    @NotBlank
    @Column(name = "phone")
    private String phone;

//    @NotNull
//    @JoinColumn(name = "company_id")
//    private Company company; todo create company management

    @Enumerated(EnumType.POSTGRES)
    private UserRole userRole;

    @NotNull
    @Column(name = "create_at")
    private LocalDate createAt;

    @NotNull
    @Column(name = "deleted")
    private boolean deleted;
}
