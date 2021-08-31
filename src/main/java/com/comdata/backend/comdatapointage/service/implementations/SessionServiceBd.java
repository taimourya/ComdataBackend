package com.comdata.backend.comdatapointage.service.implementations;

import com.comdata.backend.comdatapointage.dao.CollaborateurRepository;
import com.comdata.backend.comdatapointage.dao.TempsRepository;
import com.comdata.backend.comdatapointage.dao.UserRepository;
import com.comdata.backend.comdatapointage.dto.PauseDto;
import com.comdata.backend.comdatapointage.dto.TempsDto;
import com.comdata.backend.comdatapointage.entity.*;
import com.comdata.backend.comdatapointage.service.DtoParser;
import com.comdata.backend.comdatapointage.service.interfaces.IGetterIdService;
import com.comdata.backend.comdatapointage.service.interfaces.ISessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Date;

@Service
public class SessionServiceBd implements ISessionService {

    @Autowired private DtoParser dtoParser;
    @Autowired private IGetterIdService getterIdService;
    @Autowired private TempsRepository tempsRepository;


    @Override
    public TempsDto startSession(String matricule) throws Exception {
        Collaborateur collaborateur = (Collaborateur)getterIdService.getUser(matricule);
        Actif actif = new Actif();
        actif.setCollaborateur(collaborateur);
        actif.setDate(new Date());
        actif.setHeur_debut(LocalTime.now());
        actif.setHeur_fin(null);
        actif = tempsRepository.save(actif);
        return dtoParser.toTempsDto(actif);
    }

    @Override
    public void endSession(Integer actif_id) throws Exception {
        Actif actif = (Actif) getterIdService.getTemps(actif_id);
        actif.setHeur_fin(LocalTime.now());
        tempsRepository.save(actif);
    }

    @Override
    public PauseDto startPause(String matricule, Integer type_id) throws Exception {
        Collaborateur collaborateur = (Collaborateur)getterIdService.getUser(matricule);
        TypePause typePause = getterIdService.getTypePause(type_id);
        Pause pause = new Pause();
        pause.setCollaborateur(collaborateur);
        pause.setTypePause(typePause);
        pause.setDate(new Date());
        pause.setHeur_debut(LocalTime.now());
        pause.setHeur_fin(null);
        pause = tempsRepository.save(pause);
        return dtoParser.toPauseDto(pause);
    }

    @Override
    public void endPause(Integer pause_id) throws Exception {
        Pause pause = (Pause) getterIdService.getTemps(pause_id);
        pause.setHeur_fin(LocalTime.now());
        tempsRepository.save(pause);
    }

    @Override
    public TempsDto startInactiviter(String matricule) throws Exception {
        Collaborateur collaborateur = (Collaborateur)getterIdService.getUser(matricule);
        Inactif inactif = new Inactif();
        inactif.setCollaborateur(collaborateur);
        inactif.setDate(new Date());
        inactif.setHeur_debut(LocalTime.now());
        inactif.setHeur_fin(null);
        inactif = tempsRepository.save(inactif);
        return dtoParser.toTempsDto(inactif);
    }

    @Override
    public void endInactiviter(Integer inactif_id) throws Exception {
        Inactif inactif = (Inactif) getterIdService.getTemps(inactif_id);
        inactif.setHeur_fin(LocalTime.now());
        tempsRepository.save(inactif);
    }


}
