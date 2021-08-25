package com.comdata.backend.comdatapointage.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Data
@NoArgsConstructor
@Entity
public class Superviseur extends User {

    @OneToOne
    private Activiter activiter;
}
