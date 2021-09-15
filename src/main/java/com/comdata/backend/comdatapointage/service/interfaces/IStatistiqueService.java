package com.comdata.backend.comdatapointage.service.interfaces;

import com.comdata.backend.comdatapointage.dto.*;

import java.util.Date;

public interface IStatistiqueService {

    //paramTime day mounth year
    StatsCollaborateurDto consulterStatistiqueCollaborateur(String matricule, String paramTime, Date dateDebut) throws Exception;

    StatsCollaborateur2Dto consulterStatistiqueCollaborateur2(String matricule, String paramTime, Date from, Date to) throws Exception;

    StatsAllColByActivite2Dto consulterStatistiqueByActivite(Integer id, String paramTime, Date from, Date to) throws Exception;

    StatsPieCollaborateurDto consulterStatsPieCollaborateur(String matricule, Date from, Date to) throws Exception;

    StatsPieByActiviteDto consulterStatsPieByActivite(Integer id, Date from, Date to) throws Exception;

}
