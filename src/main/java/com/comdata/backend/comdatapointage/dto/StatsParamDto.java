package com.comdata.backend.comdatapointage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatsParamDto {
    private String date; //year or mounth or day //2020 or Janvier or 30
    private Integer value;
}
