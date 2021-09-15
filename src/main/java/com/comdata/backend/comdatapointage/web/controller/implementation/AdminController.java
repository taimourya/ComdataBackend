package com.comdata.backend.comdatapointage.web.controller.implementation;

import ch.qos.logback.core.joran.action.IADataForComplexProperty;
import com.comdata.backend.comdatapointage.dto.*;
import com.comdata.backend.comdatapointage.request.ActiviterRequest;
import com.comdata.backend.comdatapointage.request.UserRequest;
import com.comdata.backend.comdatapointage.service.interfaces.IActiviterService;
import com.comdata.backend.comdatapointage.service.interfaces.IStatistiqueService;
import com.comdata.backend.comdatapointage.service.interfaces.ITempsService;
import com.comdata.backend.comdatapointage.service.interfaces.IUserService;
import com.comdata.backend.comdatapointage.web.controller.interfaces.IAdminController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class AdminController implements IAdminController {

    @Autowired private IUserService userService;
    @Autowired private IActiviterService activiterService;
    @Autowired private ITempsService tempsService;
    @Autowired private IStatistiqueService statistiqueService;


    @Override
    public PageDto<UserDto> getUsers(String mc, String typeUser, String filterEtatCmpt,
                                     String filterSession, Integer activiter_id,
                                     int page, int size) throws Exception {

        System.out.println("etat : " + filterEtatCmpt);
        if(typeUser.equalsIgnoreCase("admin")) {
            return userService.consulterAdmins(mc, filterEtatCmpt, page, size);
        }
        else if(typeUser.equalsIgnoreCase("superviseur")) {
            return userService.consulterSuperviseurs(mc, filterEtatCmpt, activiter_id, page, size);
        }
        else if(typeUser.equalsIgnoreCase("collaborateur")) {
            return userService.consulterCollaborateurs(mc, filterEtatCmpt, filterSession, activiter_id, page, size);
        }

        return userService.consulterUsers(mc, filterEtatCmpt, page, size);
    }

    @Override
    public UserDto getUser(String matricule) throws Exception {
        return userService.consulterUser(matricule);
    }


    @Override
    public UserDto addUser(UserRequest userRequest) throws Exception {
        return userService.addUser(userRequest);
    }

    @Override
    public UserDto editUser(String matricule, UserRequest userRequest) throws Exception {
        return userService.editUser(matricule, userRequest);
    }

    @Override
    public void enableUser(String matricule) throws Exception {
        userService.enableUser(matricule);
    }

    @Override
    public void disableUser(String matricule) throws Exception {
        userService.disableUser(matricule);
    }


    @Override
    public PageDto<ActiviterDto> getActivites(String mc, int page, int size) {
        return activiterService.consulterActivites(mc, page, size);
    }

    @Override
    public ActiviterDto getActivite(Integer id) throws Exception {
        return activiterService.consulterActiviter(id);
    }

    @Override
    public ActiviterDto addActivite(ActiviterRequest activiterRequest) {
        System.out.println("name : " + activiterRequest.getName());
        System.out.println("tina : " + activiterRequest.getTinactiviteMs());
        System.out.println("tfer : " + activiterRequest.getTfermetureSessionMs());
        return activiterService.addActiviter(activiterRequest);
    }

    @Override
    public ActiviterDto editActivite(Integer id, ActiviterRequest activiterRequest) throws Exception {
        return activiterService.editActiviter(id, activiterRequest);
    }

    @Override
    public void enableActivite(Integer id) throws Exception {
        activiterService.enableActiviter(id);
    }

    @Override
    public void disableActivite(Integer id) throws Exception {
        activiterService.disableActiviter(id);
    }

    @Override
    public CollaborateurAllTempsDto getAllTempsCollaborateur(String matricule) throws Exception {
        return tempsService.consulterAllTempsCollaborateur(matricule);
    }

    @Override
    public StatsCollaborateurDto getStatColl(String matricule, String paramTime, Date dateDebut) throws Exception {
        return statistiqueService.consulterStatistiqueCollaborateur(matricule, paramTime, dateDebut);
    }


    @Override
    public StatsCollaborateur2Dto getStatColl2(String matricule, String paramTime, Date from, Date to) throws Exception {
        if(to == null) {
            to = new Date();
        }
        return statistiqueService.consulterStatistiqueCollaborateur2(matricule, paramTime, from, to);
    }

    @Override
    public StatsAllColByActivite2Dto getStatActiviter(Integer id, String paramTime, Date from, Date to) throws Exception {
        if(to == null) {
            to = new Date();
        }
        return statistiqueService.consulterStatistiqueByActivite(id, paramTime, from, to);
    }

    @Override
    public StatsPieByActiviteDto getStatPieActiviter(Integer id, Date from, Date to) throws Exception {
        if(to == null) {
            to = new Date();
        }
        return statistiqueService.consulterStatsPieByActivite(id, from, to);
    }

}
