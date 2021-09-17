package com.comdata.backend.comdatapointage.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IExcelService {


    void importExcel(MultipartFile file) throws IOException;

    String exportActivites();
    String exportUsers();
    String exportTypes();
    String exportTemps();

    void importActivites(MultipartFile file) throws IOException;
    void importUsers(MultipartFile file) throws Exception;
    void importTypes(MultipartFile file) throws IOException;
    void importTemps(MultipartFile file) throws Exception;


}
