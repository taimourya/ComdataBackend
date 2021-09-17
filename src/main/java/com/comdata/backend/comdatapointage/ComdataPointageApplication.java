package com.comdata.backend.comdatapointage;

import com.comdata.backend.comdatapointage.dao.PauseRepository;
import com.comdata.backend.comdatapointage.entity.Actif;
import com.comdata.backend.comdatapointage.entity.Collaborateur;
import com.comdata.backend.comdatapointage.entity.Pause;
import com.comdata.backend.comdatapointage.service.interfaces.IDataInitialiser;
import com.comdata.backend.comdatapointage.service.interfaces.IGetterIdService;
import com.comdata.backend.comdatapointage.service.interfaces.ISessionService;
import com.comdata.backend.comdatapointage.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalTime;

@SpringBootApplication
public class ComdataPointageApplication implements CommandLineRunner {

    @Autowired
    private IDataInitialiser dataInitialiser;


    public static void main(String[] args) {
        SpringApplication.run(ComdataPointageApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void run(String... args) throws Exception {

        dataInitialiser.initActiviter();
        dataInitialiser.initTypePause();
        dataInitialiser.initAdmin();
        dataInitialiser.initSuperviseur();
        dataInitialiser.initCollaborateur();
        System.out.println("fin init");



    }
}
