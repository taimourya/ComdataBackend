package com.comdata.backend.comdatapointage.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatsCollaborateur2Dto {
    private String collaborateurMatricule;
    private String collaborateurFullName;
    private List<StatsParamsByDateDto> params = new ArrayList<>();
}
