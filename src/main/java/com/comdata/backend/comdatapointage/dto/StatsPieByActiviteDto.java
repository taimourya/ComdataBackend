package com.comdata.backend.comdatapointage.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatsPieByActiviteDto {
    private Integer activiter_id;
    private String activiter_name;
    private double activitesPercent;
    private double pausesPercent;
    private double inactivitesPercent;
    private List<StatsPieCollaborateurDto> statsCollaborateurs = new ArrayList<>();
}
