package com.comdata.backend.comdatapointage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatsPieCollaborateurDto {

    private String collaborateur_matricule;
    private String collaborateur_fullname;
    private double activitesPercent;
    private double pausesPercent;
    private double inactivitesPercent;

}
