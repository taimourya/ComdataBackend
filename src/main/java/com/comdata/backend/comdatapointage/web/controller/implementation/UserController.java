package com.comdata.backend.comdatapointage.web.controller.implementation;

import com.comdata.backend.comdatapointage.dto.UserDto;
import com.comdata.backend.comdatapointage.service.interfaces.IUserService;
import com.comdata.backend.comdatapointage.web.controller.interfaces.IUserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements IUserController {

    @Autowired private IUserService userService;

    @Override
    public UserDto getProfil() throws Exception {
        String matricule = userService.getAuthMatricule();
        return userService.consulterUser(matricule);
    }

}
