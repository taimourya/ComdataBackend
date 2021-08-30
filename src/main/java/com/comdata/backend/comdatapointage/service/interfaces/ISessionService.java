package com.comdata.backend.comdatapointage.service.interfaces;


public interface ISessionService {

    void startSession(String matricule) throws Exception;
    void endSession(Integer actif_id) throws Exception;
    void startPause(String matricule, Integer type_id) throws Exception;
    void endPause(Integer pause_id) throws Exception;
    void startInactiviter(String matricule) throws Exception;
    void endInactiviter(Integer inactif_id) throws Exception;

}
