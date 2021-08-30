package com.comdata.backend.comdatapointage;

import com.comdata.backend.comdatapointage.dao.CollaborateurRepository;
import com.comdata.backend.comdatapointage.dto.*;
import com.comdata.backend.comdatapointage.entity.*;
import com.comdata.backend.comdatapointage.service.DtoParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@SpringBootTest
public class DtoTest {

    @Autowired
    private DtoParser dtoParser;
    @Test
    public void dtoAdminParserTest() {
        Admin admin = new Admin(
                "mat1", "yahya", "taimourya",
                "email", "phone", "cin", "adresse",
                "passwd", new Date(), new Date(), true
        );

        UserDto userDto = dtoParser.toUserDto(admin);

        Assertions.assertEquals("admin", userDto.getRoleName());
        Assertions.assertEquals("yahya taimourya", userDto.getFullName());

    }
    @Test
    public void dtoSuperviseurParserTest() {
        Superviseur superviseur = new Superviseur(
                "mat1", "yahya", "taimourya",
                "email", "phone", "cin", "adresse",
                "passwd", new Date(), new Date(), true, null
        );

        UserDto userDto = dtoParser.toUserDto(superviseur);

        Assertions.assertEquals("superviseur", userDto.getRoleName());
        Assertions.assertEquals("yahya taimourya", userDto.getFullName());

    }

    @Test
    public void dtoCollaborateurParserTest() {
        Collaborateur collaborateur = new Collaborateur(
                "mat1", "yahya", "taimourya",
                "email", "phone", "cin", "adresse",
                "passwd", new Date(), new Date(), true, null
        );

        UserDto userDto = dtoParser.toUserDto(collaborateur);

        Assertions.assertEquals("collaborateur", userDto.getRoleName());
        Assertions.assertEquals("yahya taimourya", userDto.getFullName());

    }

    @Test
    public void dtoActiviterParserTest() {

        Activiter activiter = new Activiter(
                1, "activiter1", new Date(), true, new Parametrage(1, 2, 3, null),
                null, null
        );

        ActiviterDto activiterDto = dtoParser.toActiviterDto(activiter);

        Assertions.assertEquals("activiter1", activiterDto.getNom());
        Assertions.assertEquals(3, activiterDto.getTInactiviteMs());
        Assertions.assertEquals(2, activiterDto.getTFermetureSessionMs());
    }

    @Test
    public void dtoColaborateurActiviterTest() {
        Collaborateur collaborateur = new Collaborateur(
                "mat1", "yahya", "taimourya",
                "email", "phone", "cin", "adresse",
                "passwd", new Date(), new Date(), true,
                new Activiter(
                    1, "activiter1", new Date(), true, new Parametrage(1, 2, 3, null),
                    null, null
                )
        );

        ColSupActiviterDto colSupActiviterDto = dtoParser.toColSupActiviterDto(collaborateur);

        Assertions.assertEquals("collaborateur", colSupActiviterDto.getRoleName());
        Assertions.assertEquals("yahya taimourya", colSupActiviterDto.getFullName());
        Assertions.assertEquals(1, colSupActiviterDto.getActiviterId());
        Assertions.assertEquals("activiter1", colSupActiviterDto.getActiviterName());

    }
    @Test
    public void dtoSuperviseurActiviterTest() {
        Superviseur superviseur = new Superviseur(
                "mat1", "yahya", "taimourya",
                "email", "phone", "cin", "adresse",
                "passwd", new Date(), new Date(), true,
                new Activiter(
                        1, "activiter1", new Date(), true, new Parametrage(1, 2, 3, null),
                        null, null
                )
        );

        ColSupActiviterDto colSupActiviterDto = dtoParser.toColSupActiviterDto(superviseur);

        Assertions.assertEquals("superviseur", colSupActiviterDto.getRoleName());
        Assertions.assertEquals("yahya taimourya", colSupActiviterDto.getFullName());
        Assertions.assertEquals(1, colSupActiviterDto.getActiviterId());
        Assertions.assertEquals("activiter1", colSupActiviterDto.getActiviterName());

    }

    @Test
    public void dtoAdminActiviterTest() {
        Admin admin = new Admin(
                "mat1", "yahya", "taimourya",
                "email", "phone", "cin", "adresse",
                "passwd", new Date(), new Date(), true
        );

        ColSupActiviterDto colSupActiviterDto = dtoParser.toColSupActiviterDto(admin);

        Assertions.assertEquals(null, colSupActiviterDto);
    }

    @Test
    public void dtoTempTest() {

        Actif actif = new Actif(1, new Date(), LocalTime.now(), LocalTime.now(),
                new Collaborateur(
                        "mat1", "yahya", "taimourya",
                        "email", "phone", "cin", "adresse",
                        "passwd", new Date(), new Date(), true, null
                )
        );

        TempsDto tempsDto = dtoParser.toTempsDto(actif);

        Assertions.assertEquals(actif.getDate(), tempsDto.getDate());
        Assertions.assertEquals(actif.getHeur_debut(), tempsDto.getHeur_debut());
        Assertions.assertEquals(actif.getHeur_fin(), tempsDto.getHeur_fin());

        Inactif inactif = new Inactif(1, new Date(), LocalTime.now(), LocalTime.now(),
                new Collaborateur(
                        "mat1", "yahya", "taimourya",
                        "email", "phone", "cin", "adresse",
                        "passwd", new Date(), new Date(), true, null
            )
        );

        tempsDto = dtoParser.toTempsDto(inactif);

        Assertions.assertEquals(inactif.getDate(), tempsDto.getDate());
        Assertions.assertEquals(inactif.getHeur_debut(), tempsDto.getHeur_debut());
        Assertions.assertEquals(inactif.getHeur_fin(), tempsDto.getHeur_fin());

        Pause pause = new Pause(
                1, new Date(), LocalTime.now(), LocalTime.now(),
                new TypePause(
                    1, "cafe", null
                ),
                new Collaborateur(
                "mat1", "yahya", "taimourya",
                "email", "phone", "cin", "adresse",
                "passwd", new Date(), new Date(), true, null
            )
        );

        PauseDto pauseDto = dtoParser.toPauseDto(pause);

        Assertions.assertEquals(pause.getDate(), pauseDto.getDate());
        Assertions.assertEquals(pause.getHeur_debut(), pauseDto.getHeur_debut());
        Assertions.assertEquals(pause.getHeur_fin(), pauseDto.getHeur_fin());
        Assertions.assertEquals("cafe", pauseDto.getType());
    }

    @Test
    public void dtoCollaborateurTempsActivitesTest() {

        /*Collaborateur c = collaborateurRepository.findById("matcolcol1UBER").get();

        System.out.println("total : " + c.getFirstname());
        CollaborateurTempsDto collaborateurTempsDtot = dtoParser.toCollaborateurTempsActivitesDto(c);
        System.out.println("total : " + collaborateurTempsDtot.getTotal());*/

        Collaborateur collaborateur = new Collaborateur(
                "mat1", "yahya", "taimourya",
                "email", "phone", "cin", "adresse",
                "passwd", new Date(), new Date(), true,
                new Activiter(
                        1, "activiter1", new Date(), true, new Parametrage(1, 2, 3, null),
                        null, null
                )
        );


        collaborateur.getTmpActivies().add(
                new Actif(1, new Date(),
                        LocalTime.of(10, 00, 0),
                        LocalTime.of(14, 30, 0),
                        collaborateur
                )
        );
        collaborateur.getTmpActivies().add(
                new Actif(1, new Date(),
                        LocalTime.of(10, 00, 0),
                        LocalTime.of(15, 45, 0),
                        collaborateur
                )
        );

        collaborateur.getTmpActivies().add(
                new Actif(1, new Date(),
                        LocalTime.of(10, 00, 0),
                        LocalTime.of(18, 00, 0),
                        collaborateur
                )
        );

        CollaborateurTempsDto collaborateurTempsDto = dtoParser.toCollaborateurTempsActivitesDto(collaborateur);

        Assertions.assertEquals(18.25, collaborateurTempsDto.getTotal());

        Assertions.assertEquals(3, collaborateurTempsDto.getListTemps().size());

    }

}
