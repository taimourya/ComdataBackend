package com.comdata.backend.comdatapointage.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    private String matricule;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    @Column(unique = true)
    private String cin;
    private String adresse;
    private String passwd;
    @Temporal(TemporalType.DATE)
    private Date date_naissance;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date_creation;
    private boolean isActive;

}
