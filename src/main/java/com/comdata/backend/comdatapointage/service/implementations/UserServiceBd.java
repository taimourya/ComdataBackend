package com.comdata.backend.comdatapointage.service.implementations;

import com.comdata.backend.comdatapointage.dao.AdminRepository;
import com.comdata.backend.comdatapointage.dao.CollaborateurRepository;
import com.comdata.backend.comdatapointage.dao.SuperviseurRepository;
import com.comdata.backend.comdatapointage.dao.UserRepository;
import com.comdata.backend.comdatapointage.dto.PageDto;
import com.comdata.backend.comdatapointage.dto.UserDto;
import com.comdata.backend.comdatapointage.entity.*;
import com.comdata.backend.comdatapointage.request.UserRequest;
import com.comdata.backend.comdatapointage.service.DtoParser;
import com.comdata.backend.comdatapointage.service.interfaces.IGetterIdService;
import com.comdata.backend.comdatapointage.service.interfaces.IUserService;
import com.comdata.backend.comdatapointage.threads.SessionCollaborateurThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceBd implements IUserService {

    @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired private IGetterIdService getterIdService;
    @Autowired private DtoParser dtoParser;
    @Autowired private UserRepository userRepository;
    @Autowired private AdminRepository adminRepository;
    @Autowired private SuperviseurRepository superviseurRepository;
    @Autowired private CollaborateurRepository collaborateurRepository;


    @Override
    public UserDetails loadUserByUsername(String matricule) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findById(matricule);
        if(!optionalUser.isPresent())
            throw new UsernameNotFoundException(matricule);

        User userEntity = optionalUser.get();

        Collection<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority("USER"));

        if(userEntity instanceof Admin) {
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
        }
        else if(userEntity instanceof Superviseur)
            authorities.add(new SimpleGrantedAuthority("SUPERVISEUR"));
        else if(userEntity instanceof Collaborateur)
            authorities.add(new SimpleGrantedAuthority("COLLABORATEUR"));

        return new org.springframework.security.core.userdetails.User(userEntity.getMatricule() , userEntity.getPasswd() , authorities);
    }

    @Override
    public UserDto consulterUser(String matricule) throws Exception {
        User user = getterIdService.getUser(matricule);
        if(user instanceof Admin)
           return dtoParser.toUserDto(user);
        return dtoParser.toColSupActiviterDto(user);
    }

    @Override
    public PageDto<UserDto> consulterUsers(String mc, String filterEtatCmpt, int page, int size) {
        Page<User> userPage = null;
        if(filterEtatCmpt.equalsIgnoreCase("activer") || filterEtatCmpt.equalsIgnoreCase("desactiver")) {
            userPage = userRepository.findByMcAndEtatCmpt(mc,
                    filterEtatCmpt.equalsIgnoreCase("activer"), PageRequest.of(page, size));
        }
        else {//ALL
            userPage = userRepository.findByMc(mc, PageRequest.of(page, size));
        }

        PageDto<UserDto> pageDto = new PageDto<>();
        pageDto.setPage(page);
        pageDto.setSize(size);
        pageDto.setTotalPage(userPage.getTotalPages());
        for(User user : userPage.getContent()) {
            if(user instanceof Admin)
                pageDto.getContent().add(dtoParser.toUserDto(user));
            else
                pageDto.getContent().add(dtoParser.toColSupActiviterDto(user));
        }
        return pageDto;
    }

    @Override
    public PageDto<UserDto> consulterCollaborateurs(String mc, String filterEtatCmpt, String filterSession, Integer activiter_id, int page, int size) throws Exception {
        Page<Collaborateur> userPage = null;
        if(filterEtatCmpt.equalsIgnoreCase("all")) {
            if(filterEtatCmpt.equalsIgnoreCase("activer") || filterEtatCmpt.equalsIgnoreCase("desactiver")) {
                if(activiter_id == -1) {
                    userPage = collaborateurRepository.findByMcAndEtatCmpt(mc,
                            filterEtatCmpt.equalsIgnoreCase("activer"), PageRequest.of(page, size));
                }
                else {
                    Activiter activiter = getterIdService.getActiviter(activiter_id);
                    userPage = collaborateurRepository.findByMcAndEtatCmptAndActiviter(mc, activiter,
                            filterEtatCmpt.equalsIgnoreCase("activer"), PageRequest.of(page, size));
                }
            }
            else {//ALL
                if(activiter_id == -1) {
                    userPage = collaborateurRepository.findByMc(mc, PageRequest.of(page, size));
                }
                else {
                    Activiter activiter = getterIdService.getActiviter(activiter_id);
                    userPage = collaborateurRepository.findByMcAndActiviter(mc, activiter, PageRequest.of(page, size));
                }
            }
        }
        else
        {
            //filterSession =>  all, fermer, actif, inactif, pause
            List<Collaborateur> collaborateurs = new ArrayList<>();
            if(filterSession.equalsIgnoreCase("actif")) {
                for (SessionCollaborateurThread session : SessionCollaborateurThread.sessions) {
                    if(session.isActif())
                        collaborateurs.add(session.getCollaborateur());
                }
            }
            else if(filterEtatCmpt.equalsIgnoreCase("actif"))
            {
                for (int i = 0; i < size && i < SessionCollaborateurThread.sessions.size(); i++) {
                    SessionCollaborateurThread session = SessionCollaborateurThread.sessions.get(i);
                    if(session.isActif())
                        collaborateurs.add(session.getCollaborateur());
                }
            }
            else if(filterEtatCmpt.equalsIgnoreCase("inactif"))
            {
                for (int i = 0; i < size && i < SessionCollaborateurThread.sessions.size(); i++) {
                    SessionCollaborateurThread session = SessionCollaborateurThread.sessions.get(i);
                    if(session.isInactif())
                        collaborateurs.add(session.getCollaborateur());
                }
            }
            else if(filterEtatCmpt.equalsIgnoreCase("pause"))
            {
                for (int i = 0; i < size && i < SessionCollaborateurThread.sessions.size(); i++) {
                    SessionCollaborateurThread session = SessionCollaborateurThread.sessions.get(i);
                    if(session.isPause())
                        collaborateurs.add(session.getCollaborateur());
                }
            }
            else if(filterEtatCmpt.equalsIgnoreCase("fermer")) {
                List<Collaborateur> allCollaborateurs = collaborateurRepository.findAll();
                for(int i = 0; i < allCollaborateurs.size() && collaborateurs.size() < size; i++) {
                    if(!SessionCollaborateurThread.containsCollaborateur(allCollaborateurs.get(i))) {
                        collaborateurs.add(allCollaborateurs.get(i));
                    }
                }
            }
            userPage = new PageImpl<Collaborateur>(collaborateurs);
        }

        PageDto<UserDto> pageDto = new PageDto<>();
        pageDto.setPage(page);
        pageDto.setSize(size);
        pageDto.setTotalPage(userPage.getTotalPages());
        for(Collaborateur collaborateur : userPage.getContent()) {
            pageDto.getContent().add(dtoParser.toColSupActiviterDto(collaborateur));
        }
        return pageDto;
    }

    @Override
    public PageDto<UserDto> consulterSuperviseurs(String mc, String filterEtatCmpt, Integer activiter_id, int page, int size) throws Exception {
        Page<Superviseur> userPage = null;
        if(filterEtatCmpt.equalsIgnoreCase("activer") || filterEtatCmpt.equalsIgnoreCase("desactiver")) {
            if(activiter_id == -1) {
                userPage = superviseurRepository.findByMcAndEtatCmpt(mc,
                        filterEtatCmpt.equalsIgnoreCase("activer"), PageRequest.of(page, size));
            }
            else {
                Activiter activiter = getterIdService.getActiviter(activiter_id);
                userPage = superviseurRepository.findByMcAndEtatCmptAndActiviter(mc, activiter,
                        filterEtatCmpt.equalsIgnoreCase("activer"), PageRequest.of(page, size));
            }
        }
        else {//ALL
            if(activiter_id == -1) {
                userPage = superviseurRepository.findByMc(mc, PageRequest.of(page, size));
            }
            else {
                Activiter activiter = getterIdService.getActiviter(activiter_id);
                userPage = superviseurRepository.findByMcAndActiviter(mc, activiter, PageRequest.of(page, size));
            }
        }

        PageDto<UserDto> pageDto = new PageDto<>();
        pageDto.setPage(page);
        pageDto.setSize(size);
        pageDto.setTotalPage(userPage.getTotalPages());
        for(Superviseur superviseur : userPage.getContent()) {
            pageDto.getContent().add(dtoParser.toColSupActiviterDto(superviseur));
        }
        return pageDto;
    }

    @Override
    public PageDto<UserDto> consulterAdmins(String mc, String filterEtatCmpt, int page, int size) {
        Page<Admin> userPage = null;
        if(filterEtatCmpt.equalsIgnoreCase("activer") || filterEtatCmpt.equalsIgnoreCase("desactiver")) {
            userPage = adminRepository.findByMcAndEtatCmpt(mc,
                    filterEtatCmpt.equalsIgnoreCase("activer"), PageRequest.of(page, size));
        }
        else {//ALL
            userPage = adminRepository.findByMc(mc, PageRequest.of(page, size));
        }

        PageDto<UserDto> pageDto = new PageDto<>();
        pageDto.setPage(page);
        pageDto.setSize(size);
        pageDto.setTotalPage(userPage.getTotalPages());
        for(User user : userPage.getContent()) {
            if(user instanceof Admin)
                pageDto.getContent().add(dtoParser.toUserDto(user));
            else
                pageDto.getContent().add(dtoParser.toColSupActiviterDto(user));
        }
        return pageDto;
    }

    @Override
    public UserDto addUser(UserRequest request) throws Exception {
        if(!request.getPasswd().equals(request.getConfirmPasswd()))
            throw new Exception("erreur confirmation mot de passe");
        User user = null;
        String generatedMat = "";
        if(request.getType().equalsIgnoreCase("admin")) {
            user = new Admin();
            generatedMat += "ad";
        }
        else if(request.getType().equalsIgnoreCase("superviseur")) {
            user = new Superviseur();
            Activiter activiter = getterIdService.getActiviter(request.getActiviter_id());
            ((Superviseur)user).setActiviter(activiter);
            generatedMat += "su"+ request.getActiviter_id();
        }
        else if(request.getType().equalsIgnoreCase("collaborateur")) {
            user = new Collaborateur();
            Activiter activiter = getterIdService.getActiviter(request.getActiviter_id());
            ((Collaborateur)user).setActiviter(activiter);
            generatedMat += "co" + request.getActiviter_id();
        }
        else {
            throw new Exception("role indisponible");
        }

        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setActive(true);
        user.setAdresse(request.getAdresse());
        user.setCin(request.getCin());
        user.setPhone(request.getPhone());
        user.setPasswd(bCryptPasswordEncoder.encode(request.getPasswd()));
        user.setEmail(request.getEmail());
        user.setDate_naissance(request.getDate_naissance());
        user.setDate_creation(new Date());

        generatedMat += user.getCin().substring(0, 3);
        generatedMat += user.getFirstname().substring(0, 3);
        generatedMat += user.getLastname().substring(0, 3);

            try {
                int i = 1;
                do {
                    getterIdService.getUser(generatedMat);
                    //si pas d'exception => ajouter un chiffre
                    if(i != 1) {
                        //supprimer l'ancien chiffre ajouter
                        generatedMat = generatedMat.substring(0, generatedMat.length()-1);
                    }
                    generatedMat += i++;
                }while (true);
            } catch (Exception e) {
                //si exception => veut dire que le matricule n'existe pas donc pas de prob
            }

        user.setMatricule(generatedMat);
        user = userRepository.save(user);

        if(user instanceof Admin)
            return dtoParser.toUserDto(user);
        return dtoParser.toColSupActiviterDto(user);
    }

    @Override
    public UserDto editUser(String matricule, UserRequest request) throws Exception {
        if(!request.getPasswd().equals(request.getConfirmPasswd()))
            throw new Exception("erreur confirmation mot de passe");
        User user = getterIdService.getUser(matricule);

        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setAdresse(request.getAdresse());
        user.setCin(request.getCin());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setDate_naissance(request.getDate_naissance());
        user.setDate_creation(new Date());
        if(request.getPasswd().length() != 0) {
            user.setPasswd(bCryptPasswordEncoder.encode(request.getPasswd()));
        }
        user = userRepository.save(user);



        if(user instanceof Collaborateur) {
            Activiter activiter = getterIdService.getActiviter(request.getActiviter_id());
            ((Collaborateur)user).setActiviter(activiter);
            return dtoParser.toColSupActiviterDto(user);
        }
        else if(user instanceof Superviseur) {
            Activiter activiter = getterIdService.getActiviter(request.getActiviter_id());
            ((Superviseur)user).setActiviter(activiter);
            return dtoParser.toColSupActiviterDto(user);
        }
        return dtoParser.toUserDto(user);
    }

    @Override
    public void enableUser(String matricule) throws Exception {
        User user = getterIdService.getUser(matricule);
        user.setActive(true);
        userRepository.save(user);
    }

    @Override
    public void disableUser(String matricule) throws Exception {
        User user = getterIdService.getUser(matricule);
        user.setActive(false);
        userRepository.save(user);
    }

}
