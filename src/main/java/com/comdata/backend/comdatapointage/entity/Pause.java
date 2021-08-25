package com.comdata.backend.comdatapointage.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Pause extends Temps{

    @ManyToOne
    private TypePause typePause;
    @ManyToOne
    private Collaborateur collaborateur;
}
