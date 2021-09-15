package com.comdata.backend.comdatapointage.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatsAllColByActivite2Dto {
    private Integer activiter_id;
    private String activiter_name;
    private List<StatsCollaborateur2Dto> statsCollaborateurs = new ArrayList<>();
}
