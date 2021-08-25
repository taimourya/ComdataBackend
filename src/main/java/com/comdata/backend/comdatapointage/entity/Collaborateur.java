package com.comdata.backend.comdatapointage.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Collaborateur extends User {

    @ManyToOne
    private Activiter activiter;

    @OneToMany(mappedBy = "collaborateur")
    private List<Pause> pauses = new ArrayList<>();

    @OneToMany(mappedBy = "collaborateur")
    private List<Actif> tmpActivies = new ArrayList<>();

    @OneToMany(mappedBy = "collaborateur")
    private List<Inactif> tmpInactivites = new ArrayList<>();
}
