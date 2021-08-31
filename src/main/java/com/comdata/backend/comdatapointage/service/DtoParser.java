package com.comdata.backend.comdatapointage.service;

import com.comdata.backend.comdatapointage.dto.*;
import com.comdata.backend.comdatapointage.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.LocalTime;
import java.time.temporal.Temporal;
import java.util.List;

@Service
public class DtoParser {

    @Autowired
    private TimeCalculator timeCalculator;

    public UserDto toUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setMatricule(user.getMatricule());
        userDto.setCin(user.getCin());
        userDto.setFullName(user.getFirstname() + " " + user.getLastname());
        userDto.setEmail(user.getEmail());
        userDto.setPhone(user.getPhone());
        userDto.setAdresse(user.getAdresse());
        userDto.setActive(user.isActive());
        userDto.setDate_creation(user.getDate_creation());
        userDto.setDate_naissance(user.getDate_naissance());
        if(user instanceof Admin)
            userDto.setRoleName("admin");
        else if(user instanceof Superviseur)
            userDto.setRoleName("superviseur");
        else if(user instanceof Collaborateur)
            userDto.setRoleName("collaborateur");
        return userDto;
    }

    User toUser(UserDto userDto) {
        User user = new User();
        user.setMatricule(userDto.getMatricule());
        user.setCin(user.getCin());
        String[] splitedName = userDto.getFullName().split(" ");
        user.setFirstname(splitedName[0]);
        user.setFirstname(splitedName[1]);
        user.setEmail(user.getEmail());
        user.setPhone(user.getPhone());
        user.setAdresse(user.getAdresse());
        user.setActive(user.isActive());
        user.setDate_creation(user.getDate_creation());
        user.setDate_naissance(user.getDate_naissance());
        if(userDto.getRoleName() == "admin")
        {
            Admin admin = (Admin) user;
            return admin;
        }
        return user;
    }

    public ColSupActiviterDto toColSupActiviterDto(User user) {
        if(user instanceof Admin)
            return null;
        ColSupActiviterDto colSupActiviterDto = new ColSupActiviterDto();
        colSupActiviterDto.setMatricule(user.getMatricule());
        colSupActiviterDto.setCin(user.getCin());
        colSupActiviterDto.setFullName(user.getFirstname() + " " + user.getLastname());
        colSupActiviterDto.setEmail(user.getEmail());
        colSupActiviterDto.setPhone(user.getPhone());
        colSupActiviterDto.setAdresse(user.getAdresse());
        colSupActiviterDto.setActive(user.isActive());
        colSupActiviterDto.setDate_creation(user.getDate_creation());
        colSupActiviterDto.setDate_naissance(user.getDate_naissance());
        if(user instanceof Superviseur) {
            colSupActiviterDto.setRoleName("superviseur");
            colSupActiviterDto.setActiviterId(((Superviseur) user).getActiviter().getId());
            colSupActiviterDto.setActiviterName(((Superviseur) user).getActiviter().getNom());

        }
        else if(user instanceof Collaborateur) {
            colSupActiviterDto.setRoleName("collaborateur");
            colSupActiviterDto.setActiviterId(((Collaborateur) user).getActiviter().getId());
            colSupActiviterDto.setActiviterName(((Collaborateur) user).getActiviter().getNom());
        }
        return colSupActiviterDto;
    }

    public ActiviterDto toActiviterDto(Activiter activiter) {
        ActiviterDto activiterDto = new ActiviterDto();
        activiterDto.setId(activiter.getId());
        activiterDto.setNom(activiter.getNom());
        activiterDto.setDate_creation(activiter.getDate_creation());
        activiterDto.setActive(activiter.isActive());
        activiterDto.setTInactiviteMs(activiter.getParametrage().getTInactiviteMs());
        activiterDto.setTFermetureSessionMs(activiter.getParametrage().getTFermetureSessionMs());
        return activiterDto;
    }

    public TempsDto toTempsDto(Temps temps) {
        TempsDto tempsDto = new TempsDto();
        tempsDto.setId(temps.getId());
        tempsDto.setDate(temps.getDate());
        tempsDto.setHeur_debut(temps.getHeur_debut());
        tempsDto.setHeur_fin(temps.getHeur_fin());
        return tempsDto;
    }

    public PauseDto toPauseDto(Pause pause) {
        PauseDto pauseDto = new PauseDto(toTempsDto(pause), pause.getTypePause().getLibelle());
        return pauseDto;
    }

    @Transactional
    public CollaborateurTempsDto toCollaborateurTempsActivitesDto(Collaborateur collaborateur) {
        CollaborateurTempsDto collaborateurTempsDto = new CollaborateurTempsDto();
        collaborateurTempsDto.setCollaborateur_fullname(collaborateur.getFirstname() + " " + collaborateur.getLastname());
        collaborateurTempsDto.setTotal(0);
        for(Actif actif : collaborateur.getTmpActivies()) {
            collaborateurTempsDto.getListTemps().add(toTempsDto(actif));

            double difference = timeCalculator.difference(actif.getHeur_debut(), actif.getHeur_fin());
            collaborateurTempsDto.setTotal(collaborateurTempsDto.getTotal() + difference);

        }
        return collaborateurTempsDto;
    }
    @Transactional
    public CollaborateurTempsDto toCollaborateurTempsInactivitesDto(Collaborateur collaborateur) {
        CollaborateurTempsDto collaborateurTempsDto = new CollaborateurTempsDto();
        collaborateurTempsDto.setCollaborateur_fullname(collaborateur.getFirstname() + " " + collaborateur.getLastname());
        collaborateurTempsDto.setTotal(0);
        for(Inactif inactif : collaborateur.getTmpInactivites()) {
            collaborateurTempsDto.getListTemps().add(toTempsDto(inactif));

            double difference = timeCalculator.difference(inactif.getHeur_debut(), inactif.getHeur_fin());
            collaborateurTempsDto.setTotal(collaborateurTempsDto.getTotal() + difference);

        }
        return collaborateurTempsDto;
    }

    @Transactional
    public CollaborateurTempsDto toCollaborateurTempsPauseDto(Collaborateur collaborateur) {
        CollaborateurTempsDto collaborateurTempsDto = new CollaborateurTempsDto();
        collaborateurTempsDto.setCollaborateur_fullname(collaborateur.getFirstname() + " " + collaborateur.getLastname());
        collaborateurTempsDto.setTotal(0);
        for(Pause pause : collaborateur.getPauses()) {
            collaborateurTempsDto.getListTemps().add(toTempsDto(pause));
            double difference = timeCalculator.difference(pause.getHeur_debut(), pause.getHeur_fin());
            collaborateurTempsDto.setTotal(collaborateurTempsDto.getTotal() + difference);
        }
        return collaborateurTempsDto;
    }

    public <T> PageDto<T> toPageDto(Page<T> pageList, int page) {
        PageDto<T> pageDtoResult = new PageDto<>();
        pageDtoResult.setTotalPage(pageList.getTotalPages());
        pageDtoResult.setPage(page);
        pageDtoResult.setSize(pageList.getSize());
        pageDtoResult.setContent(pageList.getContent());

        return pageDtoResult;
    }

    public TypeDto toTypeDto(TypePause type) {
        TypeDto typeDto = new TypeDto();
        typeDto.setId(type.getId());
        typeDto.setLibelle(type.getLibelle());
        return typeDto;
    }

}
