package com.comdata.backend.comdatapointage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
public class CollaborateurTempsDto extends UserDto{

    private List<TempsDto> listTemps;
    private LocalTime total;

}
