package com.comdata.backend.comdatapointage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TempsDto {
    private Integer id;
    private Date date;
    private LocalTime heur_debut;
    private LocalTime heur_fin;
}
