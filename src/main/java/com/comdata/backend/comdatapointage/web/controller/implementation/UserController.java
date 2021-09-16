package com.comdata.backend.comdatapointage.web.controller.implementation;

import com.comdata.backend.comdatapointage.dto.PageDto;
import com.comdata.backend.comdatapointage.dto.TypeDto;
import com.comdata.backend.comdatapointage.dto.UserDto;
import com.comdata.backend.comdatapointage.entity.Collaborateur;
import com.comdata.backend.comdatapointage.entity.Superviseur;
import com.comdata.backend.comdatapointage.entity.User;
import com.comdata.backend.comdatapointage.request.UserRequest;
import com.comdata.backend.comdatapointage.service.FileStorageService;
import com.comdata.backend.comdatapointage.service.interfaces.IGetterIdService;
import com.comdata.backend.comdatapointage.service.interfaces.ITypePauseService;
import com.comdata.backend.comdatapointage.service.interfaces.IUserService;
import com.comdata.backend.comdatapointage.web.controller.interfaces.IUserController;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class UserController implements IUserController {

    @Autowired private IUserService userService;
    @Autowired private ITypePauseService typePauseService;
    @Autowired private IGetterIdService getterIdService;
    @Autowired private FileStorageService fileStorageService;

    @Override
    public UserDto getProfil() throws Exception {
        String matricule = userService.getAuthMatricule();
        return userService.consulterUser(matricule);
    }

    @Override
    public UserDto editProfil(UserRequest userRequest) throws Exception {
        User user = getterIdService.getUser(userService.getAuthMatricule());
        if(user instanceof Superviseur) {
            userRequest.setActiviter_id(((Superviseur) user).getActiviter().getId());
        }
        else if(user instanceof Collaborateur) {
            userRequest.setActiviter_id(((Collaborateur) user).getActiviter().getId());
        }
        return userService.editUser(userService.getAuthMatricule(), userRequest);
    }

    @Override
    public PageDto<TypeDto> getTypes() throws Exception {
        return typePauseService.consulterTypes("", 0, 1000);
    }

    @Override
    public UserDto uploadFile(MultipartFile image) throws Exception {

        if(!image.getOriginalFilename().endsWith(".png")
                && !image.getOriginalFilename().endsWith(".jpg")
                && !image.getOriginalFilename().endsWith(".jpeg")
                && !image.getOriginalFilename().endsWith(".bmp")
        ) {
            throw new Exception("not supported");
        }
        String fileName = fileStorageService.storeFile(image);
        String matricule = userService.getAuthMatricule();
        User user = getterIdService.getUser(matricule);
        if(!user.getImage().equals("default_image.png"))
            fileStorageService.deleteFile(user.getImage());
        return userService.changeImage(matricule, fileName);
    }

    @Override
    public ResponseEntity<Resource> getImage(String imageName, HttpServletRequest request) throws Exception {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(imageName);

        // Try to determine file's content type
        String contentType = null;
        contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
