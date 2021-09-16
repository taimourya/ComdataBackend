package com.comdata.backend.comdatapointage.service.implementations;

import com.comdata.backend.comdatapointage.dao.ActifRepository;
import com.comdata.backend.comdatapointage.dao.InactifRepository;
import com.comdata.backend.comdatapointage.dao.PauseRepository;
import com.comdata.backend.comdatapointage.dto.*;
import com.comdata.backend.comdatapointage.entity.*;
import com.comdata.backend.comdatapointage.service.DtoParser;
import com.comdata.backend.comdatapointage.service.TimeCalculator;
import com.comdata.backend.comdatapointage.service.interfaces.IGetterIdService;
import com.comdata.backend.comdatapointage.service.interfaces.IStatistiqueService;
import com.comdata.backend.comdatapointage.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

@Service
public class StatistiqueServiceDb implements IStatistiqueService {

    @Autowired private IUserService userService;
    @Autowired private IGetterIdService getterIdService;
    @Autowired private TimeCalculator timeCalculator;
    @Autowired private ActifRepository actifRepository;
    @Autowired private InactifRepository inactifRepository;
    @Autowired private PauseRepository pauseRepository;
    @Autowired private DtoParser dtoParser;

    @Override
    @Transactional
    public StatsCollaborateurDto consulterStatistiqueCollaborateur(String matricule, String paramTime, Date dateDebut) throws Exception {

        StatsCollaborateurDto statsCollaborateurDto = new StatsCollaborateurDto();
        Collaborateur collaborateur = (Collaborateur) getterIdService.getUser(matricule);

        statsCollaborateurDto.setCollaborateurMatricule(matricule);
        statsCollaborateurDto.setCollaborateurFullName(collaborateur.getFirstname() + " " + collaborateur.getLastname());

        statsCollaborateurDto.setStatsActif(dtoParser.toStatsParamsDto(actifRepository.findByAfterDate(collaborateur, dateDebut), paramTime));
        statsCollaborateurDto.setStatsInactif(dtoParser.toStatsParamsDto(inactifRepository.findByAfterDate(collaborateur, dateDebut), paramTime));
        statsCollaborateurDto.setStatsPause(dtoParser.toStatsParamsDto(pauseRepository.findByAfterDate(collaborateur, dateDebut), paramTime));

        return statsCollaborateurDto;
    }



    @Override
    @Transactional
    public StatsCollaborateur2Dto consulterStatistiqueCollaborateur2(String matricule, String paramTime, Date from, Date to) throws Exception {

        Collaborateur collaborateur = (Collaborateur) getterIdService.getUser(matricule);
        StatsCollaborateur2Dto statsCollaborateur = new StatsCollaborateur2Dto();

        statsCollaborateur.setCollaborateurMatricule(matricule);
        statsCollaborateur.setCollaborateurFullName(collaborateur.getFirstname() + " " + collaborateur.getLastname());

        Calendar calendar = Calendar.getInstance();


        for(Temps t : actifRepository.findByBetweenDate(collaborateur, from, to)) {

            calendar.setTime(t.getDate());
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            System.out.println(year + " / " + month + " / " + day);

            boolean exite = false;
            for(StatsParamsByDateDto p : statsCollaborateur.getParams()) {
                calendar.setTime(p.getDate());
                if(paramTime.equalsIgnoreCase("year")) {
                    int yearP = calendar.get(Calendar.YEAR);
                    if(yearP == year) {
                        exite = true;
                        p.setTotalActif(p.getTotalActif() + timeCalculator.difference(t.getHeur_debut(), t.getHeur_fin()));
                        break;
                    }
                }
                else if(paramTime.equalsIgnoreCase("month")) {
                    int monthP = calendar.get(Calendar.MONTH);
                    if(monthP == month) {
                        exite = true;
                        p.setTotalActif(p.getTotalActif() + timeCalculator.difference(t.getHeur_debut(), t.getHeur_fin()));
                        break;
                    }
                }
                else {
                    int dayP = calendar.get(Calendar.DAY_OF_MONTH);
                    if(dayP == day) {
                        exite = true;
                        p.setTotalActif(p.getTotalActif() + timeCalculator.difference(t.getHeur_debut(), t.getHeur_fin()));
                        break;
                    }
                }
            }

            if(!exite) {
                StatsParamsByDateDto param = new StatsParamsByDateDto();
                param.setDate(t.getDate());
                param.setTotalActif(timeCalculator.difference(t.getHeur_debut(), t.getHeur_fin()));
                statsCollaborateur.getParams().add(param);
            }

        }

        for(Temps t : pauseRepository.findByBetweenDate(collaborateur, from, to)) {

            calendar.setTime(t.getDate());
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            System.out.println(year + " / " + month + " / " + day);

            boolean exite = false;
            for(StatsParamsByDateDto p : statsCollaborateur.getParams()) {
                calendar.setTime(p.getDate());
                if(paramTime.equalsIgnoreCase("year")) {
                    int yearP = calendar.get(Calendar.YEAR);
                    if(yearP == year) {
                        exite = true;
                        p.setTotalPause(p.getTotalPause() + timeCalculator.difference(t.getHeur_debut(), t.getHeur_fin()));
                        break;
                    }
                }
                else if(paramTime.equalsIgnoreCase("month")) {
                    int monthP = calendar.get(Calendar.MONTH);
                    if(monthP == month) {
                        exite = true;
                        p.setTotalPause(p.getTotalPause() + timeCalculator.difference(t.getHeur_debut(), t.getHeur_fin()));
                        break;
                    }
                }
                else {
                    int dayP = calendar.get(Calendar.DAY_OF_MONTH);
                    if(dayP == day) {
                        exite = true;
                        p.setTotalPause(p.getTotalPause() + timeCalculator.difference(t.getHeur_debut(), t.getHeur_fin()));
                        break;
                    }
                }
            }

            if(!exite) {
                StatsParamsByDateDto param = new StatsParamsByDateDto();
                param.setDate(t.getDate());
                param.setTotalPause(timeCalculator.difference(t.getHeur_debut(), t.getHeur_fin()));
                statsCollaborateur.getParams().add(param);
            }

        }

        for(Temps t : inactifRepository.findByBetweenDate(collaborateur, from, to)) {

            calendar.setTime(t.getDate());
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            System.out.println(year + " / " + month + " / " + day);

            boolean exite = false;
            for(StatsParamsByDateDto p : statsCollaborateur.getParams()) {
                calendar.setTime(p.getDate());
                if(paramTime.equalsIgnoreCase("year")) {
                    int yearP = calendar.get(Calendar.YEAR);
                    if(yearP == year) {
                        exite = true;
                        p.setTotalInactif(p.getTotalInactif() + timeCalculator.difference(t.getHeur_debut(), t.getHeur_fin()));
                        break;
                    }
                }
                else if(paramTime.equalsIgnoreCase("month")) {
                    int monthP = calendar.get(Calendar.MONTH);
                    if(monthP == month) {
                        exite = true;
                        p.setTotalInactif(p.getTotalInactif() + timeCalculator.difference(t.getHeur_debut(), t.getHeur_fin()));
                        break;
                    }
                }
                else {
                    int dayP = calendar.get(Calendar.DAY_OF_MONTH);
                    if(dayP == day) {
                        exite = true;
                        p.setTotalInactif(p.getTotalInactif() + timeCalculator.difference(t.getHeur_debut(), t.getHeur_fin()));
                        break;
                    }
                }
            }

            if(!exite) {
                StatsParamsByDateDto param = new StatsParamsByDateDto();
                param.setDate(t.getDate());
                param.setTotalInactif(timeCalculator.difference(t.getHeur_debut(), t.getHeur_fin()));
                statsCollaborateur.getParams().add(param);
            }

        }

        Collections.sort(statsCollaborateur.getParams());

        return statsCollaborateur;
    }

    @Override
    @Transactional
    public StatsAllColByActivite2Dto consulterStatistiqueByActivite(Integer id, String paramTime, Date from, Date to) throws Exception {

        StatsAllColByActivite2Dto statsActivite = new StatsAllColByActivite2Dto();
        Activiter activiter = getterIdService.getActiviter(id);
        statsActivite.setActiviter_id(id);
        statsActivite.setActiviter_name(activiter.getNom());

        for(Collaborateur collaborateur : activiter.getCollaborateurs()) {
            statsActivite.getStatsCollaborateurs().add(consulterStatistiqueCollaborateur2(
                    collaborateur.getMatricule(),
                    paramTime,
                    from,
                    to
            ));
        }

        return statsActivite;
    }

    @Override
    public StatsPieCollaborateurDto consulterStatsPieCollaborateur(String matricule, Date from, Date to) throws Exception {

        StatsPieCollaborateurDto statsPie = new StatsPieCollaborateurDto();
        Collaborateur collaborateur = (Collaborateur) getterIdService.getUser(matricule);

        statsPie.setCollaborateur_matricule(collaborateur.getMatricule());
        statsPie.setCollaborateur_fullname(collaborateur.getFirstname() + " " + collaborateur.getLastname());

        double totalActivites = 0;
        double totalPauses = 0;
        double totalInactivites = 0;

        for(Temps t : actifRepository.findByBetweenDate(collaborateur, from, to)) {
            totalActivites += timeCalculator.difference(t.getHeur_debut(), t.getHeur_fin());
        }
        for(Temps t : pauseRepository.findByBetweenDate(collaborateur, from, to)) {
            totalPauses += timeCalculator.difference(t.getHeur_debut(), t.getHeur_fin());
        }
        for(Temps t : inactifRepository.findByBetweenDate(collaborateur, from, to)) {
            totalInactivites += timeCalculator.difference(t.getHeur_debut(), t.getHeur_fin());
        }

        double total = totalActivites + totalPauses + totalInactivites;

        if(total != 0) {
            statsPie.setActivitesPercent((totalActivites * 100) / total);
            statsPie.setPausesPercent((totalPauses * 100) / total);
            statsPie.setInactivitesPercent((totalInactivites * 100) / total);
        }
        else {
            statsPie.setActivitesPercent(0);
            statsPie.setPausesPercent(0);
            statsPie.setInactivitesPercent(0);
        }

        return statsPie;
    }

    @Override
    @Transactional
    public StatsPieByActiviteDto consulterStatsPieByActivite(Integer id, Date from, Date to) throws Exception {

        StatsPieByActiviteDto statsActivite = new StatsPieByActiviteDto();
        Activiter activiter = getterIdService.getActiviter(id);
        statsActivite.setActiviter_id(id);
        statsActivite.setActiviter_name(activiter.getNom());

        for(Collaborateur c : activiter.getCollaborateurs()) {
            statsActivite.getStatsCollaborateurs().add(consulterStatsPieCollaborateur(
                   c.getMatricule(),
                   from,
                   to
            ));
        }

        return statsActivite;
    }


}
