package com.comdata.backend.comdatapointage.service.interfaces;

import com.comdata.backend.comdatapointage.dto.CollaborateurAllTempsDto;
import com.comdata.backend.comdatapointage.dto.CollaborateurTempsDto;
import com.comdata.backend.comdatapointage.dto.PageDto;

public interface ITempsService {

    CollaborateurTempsDto consulterActivitesCollaborateur(String matricule) throws Exception;
    CollaborateurTempsDto consulterInactivitesCollaborateur(String matricule) throws Exception;
    CollaborateurTempsDto consulterPausesCollaborateur(String matricule) throws Exception;

    CollaborateurAllTempsDto consulterAllTempsCollaborateur(String matricule) throws Exception;


}
