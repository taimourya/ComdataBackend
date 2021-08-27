package com.comdata.backend.comdatapointage.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalTime;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
public class Actif extends Temps{
    @ManyToOne
    private Collaborateur collaborateur;

    public Actif(Integer id, Date date, LocalTime heur_debut, LocalTime heur_fin, Collaborateur collaborateur) {
        super(id, date, heur_debut, heur_fin);
        this.collaborateur = collaborateur;
    }
}
