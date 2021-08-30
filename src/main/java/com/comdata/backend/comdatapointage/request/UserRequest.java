package com.comdata.backend.comdatapointage.request;

import lombok.Data;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
public class UserRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String cin;
    private String adresse;
    private String passwd;
    private String confirmPasswd;
    private Date date_naissance;
}
