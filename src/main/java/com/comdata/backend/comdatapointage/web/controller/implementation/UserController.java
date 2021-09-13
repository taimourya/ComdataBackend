package com.comdata.backend.comdatapointage.web.controller.implementation;

import com.comdata.backend.comdatapointage.dto.PageDto;
import com.comdata.backend.comdatapointage.dto.TypeDto;
import com.comdata.backend.comdatapointage.dto.UserDto;
import com.comdata.backend.comdatapointage.entity.Collaborateur;
import com.comdata.backend.comdatapointage.entity.Superviseur;
import com.comdata.backend.comdatapointage.entity.User;
import com.comdata.backend.comdatapointage.request.UserRequest;
import com.comdata.backend.comdatapointage.service.interfaces.IGetterIdService;
import com.comdata.backend.comdatapointage.service.interfaces.ITypePauseService;
import com.comdata.backend.comdatapointage.service.interfaces.IUserService;
import com.comdata.backend.comdatapointage.web.controller.interfaces.IUserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements IUserController {

    @Autowired private IUserService userService;
    @Autowired private ITypePauseService typePauseService;
    @Autowired private IGetterIdService getterIdService;

    @Override
    public UserDto getProfil() throws Exception {
        String matricule = userService.getAuthMatricule();
        return userService.consulterUser(matricule);
    }

    @Override
    public UserDto editProfil(UserRequest userRequest) throws Exception {
        User user = getterIdService.getUser(userService.getAuthMatricule());
        if(user instanceof Superviseur) {
            userRequest.setActiviter_id(((Superviseur) user).getActiviter().getId());
        }
        else if(user instanceof Collaborateur) {
            userRequest.setActiviter_id(((Collaborateur) user).getActiviter().getId());
        }
        return userService.editUser(userService.getAuthMatricule(), userRequest);
    }

    @Override
    public PageDto<TypeDto> getTypes() throws Exception {
        return typePauseService.consulterTypes("", 0, 1000);
    }

}
