package com.comdata.backend.comdatapointage;

import com.comdata.backend.comdatapointage.dto.ActiviterDto;
import com.comdata.backend.comdatapointage.dto.PageDto;
import com.comdata.backend.comdatapointage.request.ActiviterRequest;
import com.comdata.backend.comdatapointage.service.implementations.ActiviterServiceDb;
import com.comdata.backend.comdatapointage.service.interfaces.IActiviterService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

@SpringBootTest
public class ActiviterServiceTest {

    @Autowired private IActiviterService activiterService;

    @Test
    public void consulterActiviterTest() {

        try {
            ActiviterDto activiterDto = activiterService.consulterActiviter(1);

            Assertions.assertEquals("UBER", activiterDto.getNom());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void consulterActivitesTest() {
        String[] activites = {"UBER","ORANGE","COMDATA","TEST"};

        PageDto<ActiviterDto> pageDto = activiterService.consulterActivites("", 0, 4);

        for(int i = 0; i < activites.length; i++) {
            Assertions.assertEquals(activites[i], pageDto.getContent().get(i).getNom());
        }

    }

    @Test
    public void addActiviteTest() {
        ActiviterRequest activiterRequest = new ActiviterRequest();
        activiterRequest.setName("new");
        activiterRequest.setTFermetureSessionMs(1000);
        activiterRequest.setTInactiviteMs(1500);
        ActiviterDto activiterDto = activiterService.addActiviter(activiterRequest);

        Assertions.assertEquals("new", activiterDto.getNom());
        Assertions.assertEquals(1000, activiterDto.getTFermetureSessionMs());
        Assertions.assertEquals(1500, activiterDto.getTInactiviteMs());

    }

    @Test
    public void editActiviterTest() {
        ActiviterRequest activiterRequest = new ActiviterRequest();
        activiterRequest.setName("new");
        activiterRequest.setTFermetureSessionMs(1000);
        activiterRequest.setTInactiviteMs(1500);
        ActiviterDto activiterDto = activiterService.addActiviter(activiterRequest);

        activiterRequest.setName("newedit");
        activiterRequest.setTFermetureSessionMs(800);
        activiterRequest.setTInactiviteMs(900);

        try {
            activiterDto = activiterService.editActiviter(activiterDto.getId(), activiterRequest);

            Assertions.assertEquals("newedit", activiterDto.getNom());
            Assertions.assertEquals(800, activiterDto.getTFermetureSessionMs());
            Assertions.assertEquals(900, activiterDto.getTInactiviteMs());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void disableActiviterTest() {
        try {
            ActiviterDto activiterDto = activiterService.consulterActiviter(1);
            activiterService.disableActiviter(activiterDto.getId());
            activiterDto = activiterService.consulterActiviter(1);
            Assertions.assertEquals("UBER", activiterDto.getNom());
            Assertions.assertEquals(false, activiterDto.isActive());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void enableActiviterTest() {
        try {
            ActiviterDto activiterDto = activiterService.consulterActiviter(1);
            activiterService.disableActiviter(activiterDto.getId());
            activiterDto = activiterService.consulterActiviter(1);
            Assertions.assertEquals("UBER", activiterDto.getNom());
            Assertions.assertEquals(false, activiterDto.isActive());

            activiterService.enableActiviter(activiterDto.getId());
            activiterDto = activiterService.consulterActiviter(1);

            Assertions.assertEquals("UBER", activiterDto.getNom());
            Assertions.assertEquals(true, activiterDto.isActive());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
