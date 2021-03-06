package com.comdata.backend.comdatapointage.service.implementations;

import com.comdata.backend.comdatapointage.dto.CollaborateurAllTempsDto;
import com.comdata.backend.comdatapointage.dto.CollaborateurTempsDto;
import com.comdata.backend.comdatapointage.entity.Collaborateur;
import com.comdata.backend.comdatapointage.service.DtoParser;
import com.comdata.backend.comdatapointage.service.interfaces.IGetterIdService;
import com.comdata.backend.comdatapointage.service.interfaces.ITempsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TempsServiceBd implements ITempsService {

    @Autowired private DtoParser dtoParser;
    @Autowired private IGetterIdService getterIdService;

    //RAP EAGER


    @Override
    public CollaborateurTempsDto consulterActivitesCollaborateur(String matricule) throws Exception {
        return dtoParser.toCollaborateurTempsActivitesDto((Collaborateur) getterIdService.getUser(matricule));
    }

    @Override
    public CollaborateurTempsDto consulterInactivitesCollaborateur(String matricule) throws Exception {
        return dtoParser.toCollaborateurTempsActivitesDto((Collaborateur) getterIdService.getUser(matricule));
    }

    @Override
    public CollaborateurTempsDto consulterPausesCollaborateur(String matricule) throws Exception {
        return dtoParser.toCollaborateurTempsActivitesDto((Collaborateur) getterIdService.getUser(matricule));
    }

    @Override
    public CollaborateurAllTempsDto consulterAllTempsCollaborateur(String matricule) throws Exception {
        return dtoParser.toCollaborateurAllTempsDtoDto((Collaborateur) getterIdService.getUser(matricule));
    }

}
