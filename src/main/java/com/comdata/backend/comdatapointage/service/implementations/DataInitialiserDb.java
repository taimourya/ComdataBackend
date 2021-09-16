package com.comdata.backend.comdatapointage.service.implementations;

import com.comdata.backend.comdatapointage.dao.*;
import com.comdata.backend.comdatapointage.entity.*;
import com.comdata.backend.comdatapointage.service.interfaces.IDataInitialiser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.stream.Stream;

@Service
public class DataInitialiserDb implements IDataInitialiser {

    @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired private UserRepository userRepository;
    @Autowired private TempsRepository tempsRepository;
    @Autowired private ActiviterRepository activiterRepository;
    @Autowired private ParametrageRepository parametrageRepository;
    @Autowired private TypePauseRepository typePauseRepository;

    @Override
    public void initActiviter() {

        Stream.of("UBER","ORANGE","COMDATA","TEST").forEach(e -> {

            Parametrage parametrage = new Parametrage(
                    null, 10000, 10000, null
            );
            parametrage = parametrageRepository.save(parametrage);

            Activiter activiter = new Activiter(
                    null, e,new Date(), true,
                    parametrage, null, null
            );
            activiter = activiterRepository.save(activiter);


        });
    }

    @Override
    public void initTypePause() {
        Stream.of("Pause café","Pause déjeuner","Pause cigarette").forEach(e -> {
            TypePause typePause = new TypePause(null, e, null);
            typePauseRepository.save(typePause);
        });
    }

    @Override
    public void initAdmin() {
        Stream.of("admin", "yahya", "amine").forEach(e -> {
            Admin admin = new Admin(
              "matadmin"+e, e, e, e+"@gmail.com", "0643334135",
              "BE"+e, "adresse "+e, bCryptPasswordEncoder.encode("123"),
                    new Date(), new Date(), true
            );
            admin.setImage("default_image.png");
            userRepository.save(admin);
        });
    }

    @Override
    public void initSuperviseur() {
        activiterRepository.findAll().forEach(a -> {
            Stream.of("sup1"+a.getNom(), "sup2"+a.getNom(), "sup3"+a.getNom()).forEach(e -> {
                Superviseur s = new Superviseur(
                        "matsup"+e, e, e, e+"@gmail.com", "0643334135",
                        "BE"+e, "adresse "+e, bCryptPasswordEncoder.encode("123"),
                        new Date(), new Date(), true, a
                );
                s.setImage("default_image.png");
                userRepository.save(s);
            });
        });

    }

    @Override
    public void initCollaborateur() {
        activiterRepository.findAll().forEach(a -> {
            Stream.of("col1"+a.getNom(), "col2"+a.getNom(), "col3"+a.getNom()).forEach(e -> {
                Collaborateur c = new Collaborateur(
                        "matcol"+e, e, e, e+"@gmail.com", "0643334135",
                        "BE"+e, "adresse "+e, bCryptPasswordEncoder.encode("123"),
                        new Date(), new Date(), true, a
                );
                c.setImage("default_image.png");
                c = userRepository.save(c);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                for(int i = 1; i <= 9; i++) {
                    try {
                        Actif actif = new Actif(
                                null,
                                dateFormat.parse("2021-07-0"+i),
                                LocalTime.of(10, 30, 0),
                                LocalTime.of(12, 30, 0),
                                c
                        );
                        tempsRepository.save(actif);
                        actif = new Actif(
                                null,
                                dateFormat.parse("2021-08-0"+i),
                                LocalTime.of(10, 30, 0),
                                LocalTime.of(14, 30, 0),
                                c
                        );
                        tempsRepository.save(actif);
                        actif = new Actif(
                                null,
                                dateFormat.parse("2021-09-0"+i),
                                LocalTime.of(10, 30, 0),
                                LocalTime.of(16, 30, 0),
                                c
                        );
                        tempsRepository.save(actif);
                        Inactif inactif = new Inactif(
                                null,
                                dateFormat.parse("2021-06-0"+i),
                                LocalTime.of(11, 30, 0),
                                LocalTime.of(12, 00, 0),
                                c
                        );
                        tempsRepository.save(inactif);
                        inactif = new Inactif(
                                null,
                                dateFormat.parse("2021-07-"+i*2),
                                LocalTime.of(13, 30, 0),
                                LocalTime.of(14, 00, 0),
                                c
                        );
                        tempsRepository.save(inactif);
                        inactif = new Inactif(
                                null,
                                dateFormat.parse("2021-08-0"+i),
                                LocalTime.of(15, 30, 0),
                                LocalTime.of(16, 00, 0),
                                c
                        );
                        tempsRepository.save(inactif);


                        Pause pause = new Pause(
                                null,
                                dateFormat.parse("2021-09-0"+i),
                                LocalTime.of(10, 30, 0),
                                LocalTime.of(10, 55, 0),
                        typePauseRepository.findById(1).get(), c
                        );
                        tempsRepository.save(pause);
                        pause = new Pause(
                                null,
                                dateFormat.parse("2021-07-0"+i),
                                LocalTime.of(13, 30, 0),
                                LocalTime.of(14, 00, 0),
                                typePauseRepository.findById(2).get(), c
                        );
                        tempsRepository.save(pause);
                        pause = new Pause(
                                null,
                                dateFormat.parse("2021-07-0"+i),
                                LocalTime.of(17, 30, 0),
                                LocalTime.of(17, 55, 0),
                                typePauseRepository.findById(3).get(), c
                        );
                        tempsRepository.save(pause);
                    } catch (ParseException parseException) {
                        parseException.printStackTrace();
                    }
                }
            });
        });
    }
}
