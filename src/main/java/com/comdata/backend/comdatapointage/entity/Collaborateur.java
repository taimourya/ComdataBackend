package com.comdata.backend.comdatapointage.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Collaborateur extends User {

    @ManyToOne
    private Activiter activiter;

    @OneToMany(mappedBy = "collaborateur")
    private List<Pause> pauses = new ArrayList<>();

    @OneToMany(mappedBy = "collaborateur", fetch = FetchType.EAGER)
    private List<Actif> tmpActivies = new ArrayList<>();

    @OneToMany(mappedBy = "collaborateur")
    private List<Inactif> tmpInactivites = new ArrayList<>();

    public Collaborateur(String matricule, String firstname, String lastname, String email, String phone, String cin, String adresse, String passwd, Date date_naissance, Date date_creation, boolean isActive, Activiter activiter) {
        super(matricule, firstname, lastname, email, phone, cin, adresse, passwd, date_naissance, date_creation, isActive);
        this.activiter = activiter;
    }
}
