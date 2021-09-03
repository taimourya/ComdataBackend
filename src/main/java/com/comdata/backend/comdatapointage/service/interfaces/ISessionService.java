package com.comdata.backend.comdatapointage.service.interfaces;


import com.comdata.backend.comdatapointage.dto.PauseDto;
import com.comdata.backend.comdatapointage.dto.TempsDto;
import com.comdata.backend.comdatapointage.entity.Actif;
import com.comdata.backend.comdatapointage.entity.Inactif;
import com.comdata.backend.comdatapointage.entity.Pause;
import com.comdata.backend.comdatapointage.entity.Temps;

public interface ISessionService {

    Actif startSession(String matricule) throws Exception;
    void endSession(Integer actif_id) throws Exception;
    public Actif startActiviter(String matricule) throws Exception;
    void endActiviter(Integer pause_id) throws Exception;
    Pause startPause(String matricule, Integer type_id) throws Exception;
    void endPause(Integer pause_id) throws Exception;
    Inactif startInactiviter(String matricule) throws Exception;
    void endInactiviter(Integer inactif_id) throws Exception;

}
