package com.comdata.backend.comdatapointage.web.controller.interfaces;

import com.comdata.backend.comdatapointage.dto.PageDto;
import com.comdata.backend.comdatapointage.dto.TypeDto;
import com.comdata.backend.comdatapointage.dto.UserDto;
import com.comdata.backend.comdatapointage.request.UserRequest;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/user")
public interface IUserController {

    @GetMapping("/profil")
    UserDto getProfil() throws Exception;
    @PutMapping("/profil/edit")
    UserDto editProfil(@RequestBody UserRequest userRequest) throws Exception;

    @GetMapping("/pause/types")
    PageDto<TypeDto> getTypes() throws Exception;

    @PostMapping("/upload/image")
    UserDto uploadFile(@RequestParam("image") MultipartFile image) throws Exception;

    @GetMapping("/image/{imageName:.+}") //autoriser par tout les utilisateurs (anonyme ou authentifier)
    ResponseEntity<Resource> getImage(@PathVariable String imageName, HttpServletRequest request) throws Exception;

    @GetMapping("/file/{fileName:.+}") //autoriser par tout les utilisateurs (anonyme ou authentifier)
    ResponseEntity<Resource> getFileExcel(@PathVariable String fileName, HttpServletRequest request) throws Exception;

}
