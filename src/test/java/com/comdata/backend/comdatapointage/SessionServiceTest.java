package com.comdata.backend.comdatapointage;

import com.comdata.backend.comdatapointage.dto.TempsDto;
import com.comdata.backend.comdatapointage.service.interfaces.IGetterIdService;
import com.comdata.backend.comdatapointage.service.interfaces.ISessionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class SessionServiceTest {

    @Autowired private IGetterIdService getterIdService;
    @Autowired private ISessionService sessionService;

    @Test
    public void startSessionTest() {

        /*try {
            TempsDto tempsDto =  sessionService.startSession("matcolcol1UBER");
            Assertions.assertEquals(new TempsDto(), tempsDto);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}
