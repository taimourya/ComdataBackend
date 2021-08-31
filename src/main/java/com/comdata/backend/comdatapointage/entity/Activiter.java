package com.comdata.backend.comdatapointage.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Activiter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String nom;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date_creation;
    private boolean isActive;

    @OneToOne
    private Parametrage parametrage;

    @OneToMany(mappedBy = "activiter")
    private List<Collaborateur> collaborateurs = new ArrayList<>();

    @OneToMany(mappedBy = "activiter")
    private List<Superviseur> superviseurs = new ArrayList<>();
}
