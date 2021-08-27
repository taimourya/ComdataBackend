package com.comdata.backend.comdatapointage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActiviterDto {
    private Integer id;
    private String nom;
    private Date date_creation;
    private boolean isActive;
    private int tFermetureSessionMs;
    private int tInactiviteMs;
}
