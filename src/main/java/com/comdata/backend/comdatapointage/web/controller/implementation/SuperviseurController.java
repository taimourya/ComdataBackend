package com.comdata.backend.comdatapointage.web.controller.implementation;

import com.comdata.backend.comdatapointage.dto.*;
import com.comdata.backend.comdatapointage.entity.Superviseur;
import com.comdata.backend.comdatapointage.request.ActiviterRequest;
import com.comdata.backend.comdatapointage.request.UserRequest;
import com.comdata.backend.comdatapointage.service.interfaces.IActiviterService;
import com.comdata.backend.comdatapointage.service.interfaces.IGetterIdService;
import com.comdata.backend.comdatapointage.service.interfaces.ITempsService;
import com.comdata.backend.comdatapointage.service.interfaces.IUserService;
import com.comdata.backend.comdatapointage.web.controller.interfaces.ISuperviseurController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SuperviseurController implements ISuperviseurController {

    @Autowired private IGetterIdService getterIdService;
    @Autowired private IUserService userService;
    @Autowired private IActiviterService activiterService;
    @Autowired private ITempsService tempsService;

    @Override
    public PageDto<UserDto> getCollaborateurs(String mc, String filterEtatCmpt, String filterSession, int page, int size) throws Exception {

        Superviseur superviseur = (Superviseur) getterIdService.getUser(userService.getAuthMatricule());
        return userService.consulterCollaborateurs(mc, filterEtatCmpt, filterSession, superviseur.getActiviter().getId(), page, size);
    }

    @Override
    public UserDto getCollaborateur(String matricule) throws Exception {
        Superviseur superviseur = (Superviseur) getterIdService.getUser(userService.getAuthMatricule());
        UserDto collaborateur = userService.consulterUser(matricule);
        if(collaborateur.getRoleName().equals("collaborateur") && ((ColSupActiviterDto)collaborateur).getActiviterId() == superviseur.getActiviter().getId())
            return collaborateur;

        throw new Exception("vous n'avez pas le droit de visualiser ce collaborateur");
    }

    @Override
    public UserDto saveCollaborateur(UserRequest userRequest) throws Exception {
        Superviseur superviseur = (Superviseur) getterIdService.getUser(userService.getAuthMatricule());
        userRequest.setType("collaborateur");
        userRequest.setActiviter_id(superviseur.getActiviter().getId());
        return userService.addUser(userRequest);
    }

    @Override
    public UserDto editCollaborateur(String matricule, UserRequest userRequest) throws Exception {
        Superviseur superviseur = (Superviseur) getterIdService.getUser(userService.getAuthMatricule());
        UserDto collaborateur = userService.consulterUser(matricule);
        if(!collaborateur.getRoleName().equals("collaboareur")) {
            throw new Exception("vous n'avez pas le droit de modifier un superviseur");
        }
        if(((ColSupActiviterDto)collaborateur).getActiviterId() != superviseur.getActiviter().getId())
            throw new Exception("vous n'avez pas le droit de visualiser ce collaborateur");
        userRequest.setActiviter_id(superviseur.getActiviter().getId());
        return userService.editUser(matricule, userRequest);
    }

    @Override
    public void enableCollaborateur(String matricule) throws Exception {
        Superviseur superviseur = (Superviseur) getterIdService.getUser(userService.getAuthMatricule());
        UserDto collaborateur = userService.consulterUser(matricule);
        if(collaborateur.getRoleName().equals("collaborateur") && ((ColSupActiviterDto)collaborateur).getActiviterId() == superviseur.getActiviter().getId()) {
            userService.enableUser(matricule);
            return;
        }

        throw new Exception("vous n'avez pas le droit de visualiser ce collaborateur");
    }

    @Override
    public void disableCollaborateur(String matricule) throws Exception {
        Superviseur superviseur = (Superviseur) getterIdService.getUser(userService.getAuthMatricule());
        UserDto collaborateur = userService.consulterUser(matricule);
        if(collaborateur.getRoleName().equals("collaborateur") && ((ColSupActiviterDto)collaborateur).getActiviterId() == superviseur.getActiviter().getId()) {
            userService.disableUser(matricule);
            return;
        }

        throw new Exception("vous n'avez pas le droit de visualiser ce collaborateur");
    }

    @Override
    public CollaborateurAllTempsDto getAllTempsCollaborateur(String matricule) throws Exception {Superviseur superviseur = (Superviseur) getterIdService.getUser(userService.getAuthMatricule());
        UserDto collaborateur = userService.consulterUser(matricule);
        if(collaborateur.getRoleName().equals("collaborateur") && ((ColSupActiviterDto)collaborateur).getActiviterId() == superviseur.getActiviter().getId()) {
            return tempsService.consulterAllTempsCollaborateur(matricule);
        }

        throw new Exception("vous n'avez pas le droit de visualiser ce collaborateur");
    }

    @Override
    public ActiviterDto editParametrageActiviter(ActiviterRequest activiterRequest) throws Exception {
        Superviseur superviseur = (Superviseur) getterIdService.getUser(userService.getAuthMatricule());
        activiterRequest.setName(superviseur.getActiviter().getNom());

        return activiterService.editActiviter(superviseur.getActiviter().getId(), activiterRequest);
    }
}
