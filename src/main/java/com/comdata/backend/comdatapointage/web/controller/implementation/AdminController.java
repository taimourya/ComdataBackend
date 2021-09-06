package com.comdata.backend.comdatapointage.web.controller.implementation;

import ch.qos.logback.core.joran.action.IADataForComplexProperty;
import com.comdata.backend.comdatapointage.dto.ActiviterDto;
import com.comdata.backend.comdatapointage.dto.PageDto;
import com.comdata.backend.comdatapointage.dto.UserDto;
import com.comdata.backend.comdatapointage.request.ActiviterRequest;
import com.comdata.backend.comdatapointage.request.UserRequest;
import com.comdata.backend.comdatapointage.service.interfaces.IActiviterService;
import com.comdata.backend.comdatapointage.service.interfaces.IUserService;
import com.comdata.backend.comdatapointage.web.controller.interfaces.IAdminController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController implements IAdminController {

    @Autowired private IUserService userService;
    @Autowired private IActiviterService activiterService;


    @Override
    public PageDto<UserDto> getUsers(String mc, String typeUser, String filterEtatCmpt,
                                     String filterSession, Integer activiter_id,
                                     int page, int size) throws Exception {

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
}
