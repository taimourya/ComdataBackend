package com.comdata.backend.comdatapointage;

import com.comdata.backend.comdatapointage.service.interfaces.IDataInitialiser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ComdataPointageApplication implements CommandLineRunner {

    @Autowired
    private IDataInitialiser dataInitialiser;

    public static void main(String[] args) {
        SpringApplication.run(ComdataPointageApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        dataInitialiser.initActiviter();
        dataInitialiser.initTypePause();
        dataInitialiser.initAdmin();
        dataInitialiser.initSuperviseur();
        dataInitialiser.initCollaborateur();
    }
}
