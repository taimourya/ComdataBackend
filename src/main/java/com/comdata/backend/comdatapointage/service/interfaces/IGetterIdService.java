package com.comdata.backend.comdatapointage.service.interfaces;

import com.comdata.backend.comdatapointage.entity.*;

public interface IGetterIdService {

    User getUser(String matricule) throws Exception;
    Activiter getActiviter(Integer id) throws Exception;
    Activiter getActiviter(String nom);
    Temps getTemps(Integer id) throws Exception;
    Parametrage getParametrage(Integer id) throws Exception;
    TypePause getTypePause(Integer id) throws Exception;

}
