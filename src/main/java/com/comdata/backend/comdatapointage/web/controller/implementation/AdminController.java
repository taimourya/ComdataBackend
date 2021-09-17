package com.comdata.backend.comdatapointage.web.controller.implementation;

import com.comdata.backend.comdatapointage.dto.*;
import com.comdata.backend.comdatapointage.request.ActiviterRequest;
import com.comdata.backend.comdatapointage.request.TypeRequest;
import com.comdata.backend.comdatapointage.request.UserRequest;
import com.comdata.backend.comdatapointage.service.FileStorageService;
import com.comdata.backend.comdatapointage.service.interfaces.*;
import com.comdata.backend.comdatapointage.web.controller.interfaces.IAdminController;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

@RestController
public class AdminController implements IAdminController {

    @Autowired private IUserService userService;
    @Autowired private IActiviterService activiterService;
    @Autowired private ITempsService tempsService;
    @Autowired private IStatistiqueService statistiqueService;
    @Autowired private ITypePauseService typePauseService;
    @Autowired private IExcelService excelService;
    @Autowired private FileStorageService fileStorageService;

    @Override
    public PageDto<UserDto> getUsers(String mc, String typeUser, String filterEtatCmpt,
                                     String filterSession, Integer activiter_id,
                                     int page, int size) throws Exception {

        System.out.println("etat : " + filterEtatCmpt);
        if(typeUser.equalsIgnoreCase("admin")) {
            return userService.consulterAdmins(mc, filterEtatCmpt, page, size);
        }
        else if(typeUser.equalsIgnoreCase("superviseur")) {
            return userService.consulterSuperviseurs(mc, filterEtatCmpt, activiter_id, page, size);
        }
        else if(typeUser.equalsIgnoreCase("collaborateur")) {
            return userService.consulterCollaborateurs(mc, filterEtatCmpt, filterSession, activiter_id, page, size);
        }

        return userService.consulterUsers(mc, filterEtatCmpt, page, size);
    }

    @Override
    public UserDto getUser(String matricule) throws Exception {
        return userService.consulterUser(matricule);
    }


    @Override
    public UserDto addUser(UserRequest userRequest) throws Exception {
        return userService.addUser(userRequest);
    }

    @Override
    public UserDto editUser(String matricule, UserRequest userRequest) throws Exception {
        return userService.editUser(matricule, userRequest);
    }

    @Override
    public void enableUser(String matricule) throws Exception {
        userService.enableUser(matricule);
    }

    @Override
    public void disableUser(String matricule) throws Exception {
        userService.disableUser(matricule);
    }


    @Override
    public PageDto<ActiviterDto> getActivites(String mc, int page, int size) {
        return activiterService.consulterActivites(mc, page, size);
    }

    @Override
    public ActiviterDto getActivite(Integer id) throws Exception {
        return activiterService.consulterActiviter(id);
    }

    @Override
    public ActiviterDto addActivite(ActiviterRequest activiterRequest) {
        System.out.println("name : " + activiterRequest.getName());
        System.out.println("tina : " + activiterRequest.getTinactiviteMs());
        System.out.println("tfer : " + activiterRequest.getTfermetureSessionMs());
        return activiterService.addActiviter(activiterRequest);
    }

    @Override
    public ActiviterDto editActivite(Integer id, ActiviterRequest activiterRequest) throws Exception {
        return activiterService.editActiviter(id, activiterRequest);
    }

    @Override
    public void enableActivite(Integer id) throws Exception {
        activiterService.enableActiviter(id);
    }

    @Override
    public void disableActivite(Integer id) throws Exception {
        activiterService.disableActiviter(id);
    }

    @Override
    public CollaborateurAllTempsDto getAllTempsCollaborateur(String matricule) throws Exception {
        return tempsService.consulterAllTempsCollaborateur(matricule);
    }

    @Override
    public PageDto<TypeDto> getTypes(String mc, int page, int size) {
        return typePauseService.consulterTypes(mc, page, size);
    }

    @Override
    public TypeDto addType(TypeRequest typeRequest) {
        System.out.println("type : " + typeRequest.getLibelle());
        return typePauseService.addType(typeRequest);
    }

    @Override
    public TypeDto editType(Integer id, TypeRequest typeRequest) throws Exception {
        return typePauseService.editType(id, typeRequest);
    }

    @Override
    public void deleteType(Integer id) throws Exception {
        typePauseService.deleteType(id);
    }

    @Override
    public StatsCollaborateurDto getStatColl(String matricule, String paramTime, Date dateDebut) throws Exception {
        return statistiqueService.consulterStatistiqueCollaborateur(matricule, paramTime, dateDebut);
    }


    @Override
    public StatsCollaborateur2Dto getStatColl2(String matricule, String paramTime, Date from, Date to) throws Exception {
        if(to == null) {
            to = new Date();
        }
        return statistiqueService.consulterStatistiqueCollaborateur2(matricule, paramTime, from, to);
    }

    @Override
    public StatsAllColByActivite2Dto getStatActiviter(Integer id, String paramTime, Date from, Date to) throws Exception {
        if(to == null) {
            to = new Date();
        }
        return statistiqueService.consulterStatistiqueByActivite(id, paramTime, from, to);
    }

    @Override
    public StatsPieByActiviteDto getStatPieActiviter(Integer id, Date from, Date to) throws Exception {
        if(to == null) {
            to = new Date();
        }
        return statistiqueService.consulterStatsPieByActivite(id, from, to);
    }


    @Override
    public void importerFilesExcel(MultipartFile fileActivites, MultipartFile fileUsers, MultipartFile fileTypes, MultipartFile fileTemps) throws Exception {
        if(fileActivites != null) {
            if(!fileActivites.getOriginalFilename().endsWith(".xlsx")){
                throw new Exception("not supported");
            }
            excelService.importActivites(fileActivites);
        }
        if(fileUsers != null) {
            if(!fileUsers.getOriginalFilename().endsWith(".xlsx")){
                throw new Exception("not supported");
            }
            excelService.importUsers(fileUsers);
        }
        if(fileTypes != null) {
            if(!fileTypes.getOriginalFilename().endsWith(".xlsx")){
                throw new Exception("not supported");
            }
            excelService.importTypes(fileTypes);
        }
        if(fileTemps != null) {
            if(!fileTemps.getOriginalFilename().endsWith(".xlsx")){
                throw new Exception("not supported");
            }
            excelService.importTemps(fileTemps);
        }
    }


    @Override
    public String downloadActivitesExcel(HttpServletRequest request) throws Exception {
        String fileName = excelService.exportActivites();

        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/admin/file/")
                .path(fileName)
                .toUriString();
    }

    @Override
    public String downloadUsersExcel(HttpServletRequest request) throws Exception {
        String fileName = excelService.exportUsers();

        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/admin/file/")
                .path(fileName)
                .toUriString();
    }

    @Override
    public String downloadTypesExcel(HttpServletRequest request) throws Exception {
        String fileName = excelService.exportTypes();

        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/admin/file/")
                .path(fileName)
                .toUriString();
    }

    @Override
    public String downloadTempsExcel(HttpServletRequest request) throws Exception {
        String fileName = excelService.exportTemps();

        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/admin/file/")
                .path(fileName)
                .toUriString();
    }

    @Override
    public ResponseEntity<Resource> getFileExcel(String fileName, HttpServletRequest request) throws Exception {
        if(!fileName.endsWith(".xlsx")) {
            throw new Exception("not supported");
        }

        Resource resource = fileStorageService.loadFileAsResource(fileName);

        String contentType = null;
        contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
