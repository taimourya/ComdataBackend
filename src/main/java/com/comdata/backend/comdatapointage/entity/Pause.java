package com.comdata.backend.comdatapointage.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Pause extends Temps{

    @ManyToOne
    private TypePause typePause;
    @ManyToOne
    private Collaborateur collaborateur;

    public Pause(Integer id, Date date, LocalTime heur_debut, LocalTime heur_fin, TypePause typePause, Collaborateur collaborateur) {
        super(id, date, heur_debut, heur_fin);
        this.typePause = typePause;
        this.collaborateur = collaborateur;
    }
}
