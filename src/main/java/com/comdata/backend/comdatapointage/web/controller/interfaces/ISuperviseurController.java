package com.comdata.backend.comdatapointage.web.controller.interfaces;

import com.comdata.backend.comdatapointage.dto.*;
import com.comdata.backend.comdatapointage.request.ActiviterRequest;
import com.comdata.backend.comdatapointage.request.UserRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RequestMapping("/superviseur")
public interface ISuperviseurController {


    @GetMapping("/collaborateurs")
    PageDto<UserDto> getCollaborateurs(
            @RequestParam(required = false, defaultValue = "") String mc,
            @RequestParam(required = false, defaultValue = "all") String filterEtatCmpt,
            @RequestParam(required = false, defaultValue = "all") String filterSession,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) throws Exception;

    @GetMapping("collaborateur")
    UserDto getCollaborateur(@RequestParam String matricule) throws Exception;

    @PostMapping("collaborateur")
    UserDto saveCollaborateur(@RequestBody UserRequest userRequest) throws Exception;

    @PutMapping("collaborateur")
    UserDto editCollaborateur(@RequestParam String matricule, @RequestBody UserRequest userRequest) throws Exception;

    @PutMapping("collaborateur/enable")
    void enableCollaborateur(@RequestParam String matricule) throws Exception;

    @PutMapping("collaborateur/disable")
    void disableCollaborateur(@RequestParam String matricule) throws Exception;

    @GetMapping("collaborateur/temps")
    CollaborateurAllTempsDto getAllTempsCollaborateur(@RequestParam String matricule) throws Exception;

    @GetMapping("activiter/parametrage")
    ActiviterDto getActivite() throws Exception;

    @PutMapping("activiter/parametrage")
    ActiviterDto editParametrageActiviter(@RequestBody ActiviterRequest activiterRequest) throws Exception;

    @GetMapping("activiter/stats/pie")
    StatsPieByActiviteDto getStatPieActiviter(
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
            @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) Date to
    ) throws Exception;

}
