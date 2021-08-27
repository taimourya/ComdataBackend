package com.comdata.backend.comdatapointage.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatsParamsDto {

    private List<StatsParamDto> parametres = new ArrayList<>();

}
