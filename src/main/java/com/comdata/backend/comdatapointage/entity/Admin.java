package com.comdata.backend.comdatapointage.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
public class Admin extends User {

    public Admin(String matricule, String firstname, String lastname, String email, String phone, String cin, String adresse, String passwd, Date date_naissance, Date date_creation, boolean isActive) {
        super(matricule, firstname, lastname, email, phone, cin, adresse, passwd, date_naissance, date_creation, isActive);
    }
}
