package com.comdata.backend.comdatapointage;

import com.comdata.backend.comdatapointage.dto.PageDto;
import com.comdata.backend.comdatapointage.dto.TypeDto;
import com.comdata.backend.comdatapointage.request.TypeRequest;
import com.comdata.backend.comdatapointage.service.interfaces.ITypePauseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

@SpringBootTest
public class TypeServiceTest {

    @Autowired private ITypePauseService typePauseService;

    @Test
    public void consulterTypeTest() {

        try {
            TypeDto typeDto = typePauseService.consulterType(1);

            Assertions.assertEquals("Pause café", typeDto.getLibelle());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void consulterTypesTest() {
        String[] types = {"Pause café","Pause déjeuner","Pause cigarette"};

        PageDto<TypeDto> pageDto = typePauseService.consulterTypes("", 0, 3);

        Assertions.assertEquals(0, pageDto.getPage());
        Assertions.assertEquals(3, pageDto.getSize());
        //Assertions.assertEquals(1, pageDto.getTotalPage());
        for(int i = 0; i < types.length; i++) {
            Assertions.assertEquals(types[i], pageDto.getContent().get(i).getLibelle());
        }

    }

    @Test
    public void addTypeTest() {
        TypeRequest typeRequest = new TypeRequest();
        typeRequest.setLibelle("new");
        TypeDto typeDto = typePauseService.addType(typeRequest);

        Assertions.assertEquals("new", typeDto.getLibelle());

    }

    @Test
    public void editTypeTest() {
        TypeRequest typeRequest = new TypeRequest();
        typeRequest.setLibelle("new");
        TypeDto typeDto = typePauseService.addType(typeRequest);

        Assertions.assertEquals("new", typeDto.getLibelle());

        try {
            typeRequest.setLibelle("newEdit");
            typeDto = typePauseService.editType(5, typeRequest);

            Assertions.assertEquals("newEdit", typeDto.getLibelle());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
