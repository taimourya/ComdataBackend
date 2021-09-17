package com.comdata.backend.comdatapointage.web.controller.interfaces;

import com.comdata.backend.comdatapointage.dto.*;
import com.comdata.backend.comdatapointage.request.ActiviterRequest;
import com.comdata.backend.comdatapointage.request.TypeRequest;
import com.comdata.backend.comdatapointage.request.UserRequest;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

@RequestMapping("/admin")
public interface IAdminController {

    @GetMapping("/users")
    PageDto<UserDto> getUsers(
            @RequestParam(required = false, defaultValue = "") String mc,
            @RequestParam(required = false, defaultValue = "all") String typeUser,
            @RequestParam(required = false, defaultValue = "all") String filterEtatCmpt,
            @RequestParam(required = false, defaultValue = "all") String filterSession,
            @RequestParam(required = false, defaultValue = "-1") Integer activiter_id,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) throws Exception;

    @GetMapping("/user")
    UserDto getUser(@RequestParam String matricule) throws Exception;

    @PostMapping("/user")
    UserDto addUser(@RequestBody UserRequest userRequest) throws Exception;

    @PutMapping("/user")
    UserDto editUser(@RequestParam String matricule, @RequestBody UserRequest userRequest) throws Exception;

    @PutMapping("/user/enable")
    void enableUser(@RequestParam String matricule) throws Exception;

    @PutMapping("/user/disable")
    void disableUser(@RequestParam String matricule) throws Exception;

    @GetMapping("/activites")
    PageDto<ActiviterDto> getActivites(
            @RequestParam(required = false, defaultValue = "") String mc,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    );

    @GetMapping("/activite")
    ActiviterDto getActivite(@RequestParam Integer id) throws Exception;

    @PostMapping("/activite")
    ActiviterDto addActivite(@RequestBody ActiviterRequest activiterRequest);

    @PutMapping("/activite")
    ActiviterDto editActivite(@RequestParam Integer id, @RequestBody ActiviterRequest activiterRequest) throws Exception;

    @PutMapping("/activite/enable")
    void enableActivite(@RequestParam Integer id) throws Exception;

    @PutMapping("/activite/disable")
    void disableActivite(@RequestParam Integer id) throws Exception;

    @GetMapping("collaborateur/temps")
    CollaborateurAllTempsDto getAllTempsCollaborateur(@RequestParam String matricule) throws Exception;

    @GetMapping("types")
    PageDto<TypeDto> getTypes(
            @RequestParam(required = false, defaultValue = "") String mc,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    );

    @PostMapping("type")
    TypeDto addType(@RequestBody TypeRequest typeRequest);

    @PutMapping("type")
    TypeDto editType(Integer id, @RequestBody TypeRequest typeRequest) throws Exception;

    @DeleteMapping("type")
    void deleteType(Integer id) throws Exception;

    @GetMapping("collaborateur/stats2")
    StatsCollaborateurDto getStatColl(String matricule, String paramTime, @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateDebut) throws Exception;

    @GetMapping("collaborateur/stats")
    StatsCollaborateur2Dto getStatColl2(
            String matricule,
            String paramTime,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
            @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) Date to
    ) throws Exception;

    @GetMapping("activiter/stats")
    StatsAllColByActivite2Dto getStatActiviter(
            Integer id,
            String paramTime,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
            @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) Date to
    ) throws Exception;

    @GetMapping("activiter/stats/pie")
    StatsPieByActiviteDto getStatPieActiviter(
            Integer id,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
            @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) Date to
    ) throws Exception;


    @PostMapping("/import/excelex")
    void importerExcel(@RequestParam("file") MultipartFile file) throws IOException;

    @PostMapping("/import/excel")
    void importerFilesExcel(@RequestParam(value = "fileActivites", required = false) MultipartFile fileActivites,
                            @RequestParam(value = "fileUsers", required = false) MultipartFile fileUsers,
                            @RequestParam(value = "fileTypes", required = false) MultipartFile fileTypes,
                            @RequestParam(value = "fileTemps", required = false) MultipartFile fileTemps) throws Exception;


    @GetMapping("/download/activites/excel")
    String downloadActivitesExcel(HttpServletRequest request) throws Exception;

    @GetMapping("/download/users/excel")
    String downloadUsersExcel(HttpServletRequest request) throws Exception;

    @GetMapping("/download/types/excel")
    String downloadTypesExcel(HttpServletRequest request) throws Exception;

    @GetMapping("/download/temps/excel")
    String downloadTempsExcel(HttpServletRequest request) throws Exception;

}
