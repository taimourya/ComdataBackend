package com.comdata.backend.comdatapointage.dto;

import com.comdata.backend.comdatapointage.entity.Temps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class PauseDto extends TempsDto {
    private String type;

    public PauseDto(TempsDto tempsDto, String type) {
        super(tempsDto.getDate(), tempsDto.getHeur_debut(), tempsDto.getHeur_fin());
        this.type = type;
    }
}
