package com.pavel.jbsrm.client;

import com.pavel.jbsrm.common.hibernate.EnumType;
import com.pavel.jbsrm.common.hibernate.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Length(max = 80)
    @NotBlank
    @Column(name = "title")
    private String title;

    @Length(max = 80)
    @NotBlank
    @Column(name = "email")
    private String email;

    @Length(max = 80)
    @NotBlank
    @Column(name = "phone")
    private String phone;

    @Enumerated(EnumType.POSTGRES)
    private ClientRole clientRole;

    @Column(name = "is_active")
    private boolean isActive;
}
