package com.pavel.jbsrm.company;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "company")
public class Company {


    private long id;
    private String title;
    private String email;
    private String phone;
    private boolean deleted;
}
