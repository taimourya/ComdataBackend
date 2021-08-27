package com.comdata.backend.comdatapointage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String matricule;
    private String fullName;
    private String email;
    private String phone;
    private String cin;
    private String adresse;
    private Date date_naissance;
    private Date date_creation;
    private boolean isActive;
    private String roleName;
}
