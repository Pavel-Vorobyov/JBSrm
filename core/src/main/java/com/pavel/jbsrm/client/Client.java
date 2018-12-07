package com.pavel.jbsrm.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Size(max = 80)
    @NotBlank
    @Column(name = "title")
    private String title;

    @Size(max = 80)
    @NotBlank
    @Column(name = "email")
    private String email;

    @Size(max = 80)
    @NotBlank
    @Column(name = "phone")
    private String phone;

    @NotNull
    @Column(name = "is_active")
    private boolean isActive;
}
