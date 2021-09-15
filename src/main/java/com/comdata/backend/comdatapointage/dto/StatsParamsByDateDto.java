package com.comdata.backend.comdatapointage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatsParamsByDateDto implements Comparable<StatsParamsByDateDto>{
    private Date date;
    private double totalActif = 0;
    private double totalPause = 0;
    private double totalInactif = 0;

    @Override
    public int compareTo(StatsParamsByDateDto o) {
        return this.date.compareTo(o.getDate());
    }
}
