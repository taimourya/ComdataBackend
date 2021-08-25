package com.comdata.backend.comdatapointage.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Data
@NoArgsConstructor
@Entity
public class Inactif extends Temps{
    @ManyToOne
    private Collaborateur collaborateur;
}
