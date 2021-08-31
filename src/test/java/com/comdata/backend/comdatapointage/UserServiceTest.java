package com.comdata.backend.comdatapointage;

import com.comdata.backend.comdatapointage.dto.ColSupActiviterDto;
import com.comdata.backend.comdatapointage.dto.PageDto;
import com.comdata.backend.comdatapointage.dto.UserDto;
import com.comdata.backend.comdatapointage.entity.Collaborateur;
import com.comdata.backend.comdatapointage.request.UserRequest;
import com.comdata.backend.comdatapointage.service.interfaces.IUserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTest {

    @Autowired private IUserService userService;

    @Test
    @Order(1)
    public void consulterUserTest() throws Exception {

        UserDto userDto = userService.consulterUser("matcolcol1UBER");

        Assertions.assertEquals("col1UBER col1UBER", userDto.getFullName());
        Assertions.assertEquals("collaborateur", userDto.getRoleName());
        Assertions.assertEquals("UBER", ((ColSupActiviterDto)userDto).getActiviterName());

        userDto = userService.consulterUser("matsupsup1UBER");

        Assertions.assertEquals("sup1UBER sup1UBER", userDto.getFullName());
        Assertions.assertEquals("superviseur", userDto.getRoleName());
        Assertions.assertEquals("UBER", ((ColSupActiviterDto)userDto).getActiviterName());

        userDto = userService.consulterUser("matadminyahya");

        Assertions.assertEquals("yahya yahya", userDto.getFullName());
        Assertions.assertEquals("admin", userDto.getRoleName());

    }

    @Test
    @Order(2)
    public void consulterUsersTest() {

        String[] users = {"admin admin", "amine amine", "yahya yahya",
                "col1COMDATA col1COMDATA", "col1ORANGE col1ORANGE", "col1TEST col1TEST"};

        PageDto<UserDto> pageDto = userService.consulterUsers("", "ALL", 0, users.length);

        for(int i = 0; i < users.length; i++) {
            Assertions.assertEquals(users[i], pageDto.getContent().get(i).getFullName());
        }

        String[] users2 = {"admin admin", "amine amine", "yahya yahya"};

        PageDto<UserDto> pageDto2 = userService.consulterAdmins("", "ALL", 0, users2.length);

        for(int i = 0; i < users2.length; i++) {
            Assertions.assertEquals(users2[i], pageDto2.getContent().get(i).getFullName());
        }

        try {
            String[] users3 = {"sup1UBER sup1UBER", "sup2UBER sup2UBER", "sup3UBER sup3UBER", "sup1ORANGE sup1ORANGE"};

            PageDto<UserDto> pageDto3 = userService.consulterSuperviseurs("", "ALL", -1, 0, users3.length);
            for(int i = 0; i < users3.length; i++) {
                Assertions.assertEquals(users3[i], pageDto3.getContent().get(i).getFullName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String[] users3 = {"sup1UBER sup1UBER", "sup2UBER sup2UBER", "sup3UBER sup3UBER"};

            PageDto<UserDto> pageDto3 = userService.consulterSuperviseurs("", "ALL", 1, 0, users3.length);
            for(int i = 0; i < users3.length; i++) {
                Assertions.assertEquals(users3[i], pageDto3.getContent().get(i).getFullName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String[] users4 = {"col1UBER col1UBER", "col2UBER col2UBER", "col3UBER col3UBER", "col1ORANGE col1ORANGE"};

            PageDto<UserDto> pageDto4 = userService.consulterCollaborateurs("", "ALL", "all", -1, 0, users4.length);
            for(int i = 0; i < users4.length; i++) {
                Assertions.assertEquals(users4[i], pageDto4.getContent().get(i).getFullName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String[] users4 = {"col1ORANGE col1ORANGE", "col2ORANGE col2ORANGE", "col3ORANGE col3ORANGE"};

            PageDto<UserDto> pageDto4 = userService.consulterCollaborateurs("", "ALL", "all", 2, 0, users4.length);
            for(int i = 0; i < users4.length; i++) {
                Assertions.assertEquals(users4[i], pageDto4.getContent().get(i).getFullName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    @Order(3)
    public void addUserTest() throws Exception {

        UserRequest userRequest = new UserRequest();
        userRequest.setCin("BE902884");
        userRequest.setFirstname("yahya");
        userRequest.setLastname("taimourya");
        userRequest.setAdresse("sidi elbernoussi rue 4 hay lqods");
        userRequest.setPhone("0643334135");
        userRequest.setPasswd("123");
        userRequest.setConfirmPasswd("123");
        userRequest.setEmail("taimourya@gmail.com");
        userRequest.setType("admin");
        userRequest.setDate_naissance(new Date());

        UserDto userDto = userService.addUser(userRequest);

        Assertions.assertEquals("yahya taimourya", userDto.getFullName());
        Assertions.assertEquals("admin", userDto.getRoleName());
        Assertions.assertEquals("adBE9yahtai", userDto.getMatricule());

        userRequest.setCin("BE902883");
        userDto = userService.addUser(userRequest);

        Assertions.assertEquals("yahya taimourya", userDto.getFullName());
        Assertions.assertEquals("admin", userDto.getRoleName());
        Assertions.assertEquals("adBE9yahtai1", userDto.getMatricule());


        userRequest.setCin("BE902882");
        userRequest.setType("collaborateur");
        userRequest.setActiviter_id(1);
        userDto = userService.addUser(userRequest);

        Assertions.assertEquals("yahya taimourya", userDto.getFullName());
        Assertions.assertEquals("collaborateur", userDto.getRoleName());
        Assertions.assertEquals("co1BE9yahtai", userDto.getMatricule());


        userRequest.setCin("BE902881");
        userRequest.setType("superviseur");
        userRequest.setActiviter_id(2);
        userDto = userService.addUser(userRequest);

        Assertions.assertEquals("yahya taimourya", userDto.getFullName());
        Assertions.assertEquals("superviseur", userDto.getRoleName());
        Assertions.assertEquals("su2BE9yahtai", userDto.getMatricule());

    }

    @Test
    @Order(4)
    public void editUserTest() throws Exception {

        UserRequest userRequest = new UserRequest();
        userRequest.setCin("BE902889");
        userRequest.setFirstname("yahya");
        userRequest.setLastname("taimourya");
        userRequest.setAdresse("sidi elbernoussi rue 4 hay lqods");
        userRequest.setPhone("0643334135");
        userRequest.setPasswd("123");
        userRequest.setConfirmPasswd("123");
        userRequest.setEmail("taimourya@gmail.com");
        userRequest.setDate_naissance(new Date());
        userRequest.setActiviter_id(1);


        UserDto userDto = userService.editUser("matcolcol1UBER", userRequest);

        Assertions.assertEquals("yahya taimourya", userDto.getFullName());
        Assertions.assertEquals("collaborateur", userDto.getRoleName());
        Assertions.assertEquals("matcolcol1UBER", userDto.getMatricule());



    }


}
