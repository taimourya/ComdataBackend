package com.comdata.backend.comdatapointage.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
public class Superviseur extends User {

    @ManyToOne
    private Activiter activiter;

    public Superviseur(String matricule, String firstname, String lastname, String email, String phone, String cin, String adresse, String passwd, Date date_naissance, Date date_creation, boolean isActive, Activiter activiter) {
        super(matricule, firstname, lastname, email, phone, cin, adresse, passwd, date_naissance, date_creation, isActive);
        this.activiter = activiter;
    }
}
