package com.comdata.backend.comdatapointage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CollaborateurTempsDto extends UserDto{

    private String collaborateur_fullname;
    private List<TempsDto> listTemps = new ArrayList<>();
    private double total;

}
