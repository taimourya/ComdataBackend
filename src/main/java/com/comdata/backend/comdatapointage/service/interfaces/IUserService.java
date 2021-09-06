package com.comdata.backend.comdatapointage.service.interfaces;

import com.comdata.backend.comdatapointage.dto.PageDto;
import com.comdata.backend.comdatapointage.dto.UserDto;
import com.comdata.backend.comdatapointage.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public interface IUserService extends UserDetailsService {


    public String getAuthMatricule();

    UserDto consulterUser(String matricule) throws Exception;

    //mc => filtrer par mot cle
    //filterEtatCmpt => all, activer, desactiver
    PageDto<UserDto> consulterUsers(String mc, String filterEtatCmpt, int page, int size);

    //filterSession =>  all, fermer, actif, inactif, pause
    //activiter_id =>  filter par activiter si id = -1 => ALL
    PageDto<UserDto> consulterCollaborateurs(
            String mc, String filterEtatCmpt, String filterSession, Integer activiter_id, int page, int size
    ) throws Exception;

    PageDto<UserDto> consulterSuperviseurs(String mc, String filterEtatCmpt, Integer activiter_id, int page, int size) throws Exception;
    PageDto<UserDto> consulterAdmins(String mc, String filterEtatCmpt, int page, int size);

    UserDto addUser(UserRequest request) throws Exception;
    UserDto editUser(String matricule, UserRequest request) throws Exception;

    void enableUser(String matricule) throws Exception;
    void disableUser(String matricule) throws Exception;




}
