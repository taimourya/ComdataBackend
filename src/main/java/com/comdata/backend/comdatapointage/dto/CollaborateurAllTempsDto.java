package com.comdata.backend.comdatapointage.dto;

import lombok.Data;

@Data
public class CollaborateurAllTempsDto {
    String collaborateur_matricule;
    String collaborateur_fullname;
    double tempsMasse;
    CollaborateurTempsDto activites;
    CollaborateurTempsDto inactivites;
    CollaborateurTempsDto pauses;
}
