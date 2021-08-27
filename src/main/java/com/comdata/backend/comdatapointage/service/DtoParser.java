package com.comdata.backend.comdatapointage.service;

import com.comdata.backend.comdatapointage.dto.*;
import com.comdata.backend.comdatapointage.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.time.temporal.Temporal;

@Service
public class DtoParser {

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
        for(Actif actif : collaborateur.getTmpActivies()) {
            collaborateurTempsDto.getListTemps().add(toTempsDto(actif));

            LocalTime time = actif.getHeur_fin();
            time.minusHours(actif.getHeur_debut().getHour());
            time.minusMinutes(actif.getHeur_debut().getMinute());
            time.minusSeconds(actif.getHeur_debut().getSecond());

            collaborateurTempsDto.getTotal().plusHours(time.getHour());
            collaborateurTempsDto.getTotal().plusMinutes(time.getMinute());
            collaborateurTempsDto.getTotal().plusSeconds(time.getSecond());
        }
        return collaborateurTempsDto;
    }
    @Transactional
    public CollaborateurTempsDto toCollaborateurTempsInactivitesDto(Collaborateur collaborateur) {
        CollaborateurTempsDto collaborateurTempsDto = new CollaborateurTempsDto();
        collaborateurTempsDto.setTotal(LocalTime.of(0,0));
        for(Inactif inactif : collaborateur.getTmpInactivites()) {
            collaborateurTempsDto.getListTemps().add(toTempsDto(inactif));

            LocalTime time = inactif.getHeur_fin();
            time.minusHours(inactif.getHeur_debut().getHour());
            time.minusMinutes(inactif.getHeur_debut().getMinute());
            time.minusSeconds(inactif.getHeur_debut().getSecond());

            collaborateurTempsDto.getTotal().plusHours(time.getHour());
            collaborateurTempsDto.getTotal().plusMinutes(time.getMinute());
            collaborateurTempsDto.getTotal().plusSeconds(time.getSecond());

        }
        return collaborateurTempsDto;
    }
    @Transactional
    public CollaborateurTempsDto toCollaborateurTempsPauseDto(Collaborateur collaborateur) {
        CollaborateurTempsDto collaborateurTempsDto = new CollaborateurTempsDto();
        for(Pause pause : collaborateur.getPauses()) {
            collaborateurTempsDto.getListTemps().add(toTempsDto(pause));

            LocalTime time = pause.getHeur_fin();
            time.minusHours(pause.getHeur_debut().getHour());
            time.minusMinutes(pause.getHeur_debut().getMinute());
            time.minusSeconds(pause.getHeur_debut().getSecond());

            collaborateurTempsDto.getTotal().plusHours(time.getHour());
            collaborateurTempsDto.getTotal().plusMinutes(time.getMinute());
            collaborateurTempsDto.getTotal().plusSeconds(time.getSecond());
        }
        return collaborateurTempsDto;
    }
}
