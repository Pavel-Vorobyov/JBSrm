package com.pavel.jbsrm.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue
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

    @Enumerated(EnumType.STRING)
    @Column(name = "client_role")
    private ClientRole clientRole;

    @Column(name = "is_active")
    private boolean isActive;
}
