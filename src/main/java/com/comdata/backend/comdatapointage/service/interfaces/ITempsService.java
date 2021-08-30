package com.comdata.backend.comdatapointage.service.interfaces;

import com.comdata.backend.comdatapointage.dto.CollaborateurAllTempsDto;
import com.comdata.backend.comdatapointage.dto.CollaborateurTempsDto;
import com.comdata.backend.comdatapointage.dto.PageDto;

public interface ITempsService {

    CollaborateurTempsDto consulterActivitesCollaborateur(String matricule);
    CollaborateurTempsDto consulterInactivitesCollaborateur(String matricule);
    CollaborateurTempsDto consulterPausesCollaborateur(String matricule);

    CollaborateurAllTempsDto consulterAllTempsCollaborateur(String matricule);


}
