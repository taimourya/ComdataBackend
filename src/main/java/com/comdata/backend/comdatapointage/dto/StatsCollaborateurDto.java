package com.comdata.backend.comdatapointage.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatsCollaborateurDto {
    private String collaborateurMatricule;
    private String collaborateurFullName;
    private StatsParamsDto statsActif;
    private StatsParamsDto statsInactif;
    private StatsParamsDto statsPause;
}
