package com.comdata.backend.comdatapointage.service.interfaces;


import com.comdata.backend.comdatapointage.dto.PauseDto;
import com.comdata.backend.comdatapointage.dto.TempsDto;

public interface ISessionService {

    TempsDto startSession(String matricule) throws Exception;
    void endSession(Integer actif_id) throws Exception;
    PauseDto startPause(String matricule, Integer type_id) throws Exception;
    void endPause(Integer pause_id) throws Exception;
    TempsDto startInactiviter(String matricule) throws Exception;
    void endInactiviter(Integer inactif_id) throws Exception;

}
