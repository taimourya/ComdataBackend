package com.comdata.backend.comdatapointage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

import org.springframework.core.io.UrlResource;
import org.springframework.core.io.Resource;

@Service
public class FileStorageService {

    @Value("${file.upload-dir}")
    private String FILE_DIR;


    public String storeFile(MultipartFile file) throws Exception {

        String fileName = generateUniqueFileName() + "_" + StringUtils.cleanPath(file.getOriginalFilename());

        if(fileName.contains("..")) {
            throw new Exception("Sorry! Filename contains invalid path sequence " + fileName);
        }
        Path targetLocation = Paths.get(FILE_DIR + "/" + fileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        System.out.println("filename : " + fileName);
        System.out.println("path : " + targetLocation.toString());
        return fileName;
    }

    public void deleteFile(String fileName) throws IOException {
        Path filePath = Paths.get(FILE_DIR + "/" + fileName);
        Files.delete(filePath);
    }

    public Resource loadFileAsResource(String fileName) throws Exception {
        Path filePath = Paths.get(FILE_DIR + "/" + fileName);
        Resource resource = new UrlResource(filePath.toUri());
        if(resource.exists()) {
            return resource;
        }
        filePath = Paths.get(FILE_DIR + "/" + "default_image.png");
        resource = new UrlResource(filePath.toUri());
        if(resource.exists()) {
            return resource;
        }

        throw new Exception("fichier introuvable");
    }

    private String generateUniqueFileName() {
        String filename = "";
        long millis = System.currentTimeMillis();
        String datetime = new Date().toGMTString();
        datetime = datetime.replace(" ", "");
        datetime = datetime.replace(":", "");
        filename = "file" + "_" + datetime + "_" + millis;
        return filename;
    }
}
