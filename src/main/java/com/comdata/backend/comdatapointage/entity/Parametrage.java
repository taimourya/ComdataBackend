package com.comdata.backend.comdatapointage.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Parametrage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int tFermetureSessionMs;
    private int tInactiviteMs;
    @OneToOne(mappedBy = "parametrage")
    private Activiter activiter;

}
