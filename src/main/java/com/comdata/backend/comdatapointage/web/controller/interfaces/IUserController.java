package com.comdata.backend.comdatapointage.web.controller.interfaces;

import com.comdata.backend.comdatapointage.dto.PageDto;
import com.comdata.backend.comdatapointage.dto.TypeDto;
import com.comdata.backend.comdatapointage.dto.UserDto;
import com.comdata.backend.comdatapointage.request.UserRequest;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
public interface IUserController {

    @GetMapping("/profil")
    UserDto getProfil() throws Exception;
    @PutMapping("/profil/edit")
    UserDto editProfil(@RequestBody UserRequest userRequest) throws Exception;

    @GetMapping("/pause/types")
    PageDto<TypeDto> getTypes() throws Exception;
}
