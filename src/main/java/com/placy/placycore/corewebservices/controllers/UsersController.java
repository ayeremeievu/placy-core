package com.placy.placycore.corewebservices.controllers;

import com.placy.placycore.core.model.UserModel;
import com.placy.placycore.core.processes.data.ProcessInstanceData;
import com.placy.placycore.core.processes.data.RunProcessData;
import com.placy.placycore.core.processes.facades.ProcessesFacade;
import com.placy.placycore.core.processes.model.ProcessModel;
import com.placy.placycore.core.processes.services.ProcessesService;
import com.placy.placycore.core.services.UserService;
import com.placy.placycore.corewebservices.constants.CorewebservicesRouteConstants;
import com.placy.placycore.corewebservices.dto.UserCreationDto;
import com.placy.placycore.corewebservices.dto.UserDto;
import com.placy.placycore.corewebservices.mappers.UserCreationDtoToUserModelMapper;
import com.placy.placycore.corewebservices.mappers.UserModelToDtoMapper;
import com.placy.placycore.corewebservices.processes.dto.ProcessDto;
import com.placy.placycore.corewebservices.processes.dto.ProcessInstanceDto;
import com.placy.placycore.corewebservices.processes.dto.RunProcessDto;
import com.placy.placycore.corewebservices.processes.mappers.ProcessInstanceMapper;
import com.placy.placycore.corewebservices.processes.mappers.ProcessModelToDtoMapper;
import com.placy.placycore.corewebservices.processes.mappers.RunProcessMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * @author a.yeremeiev@netconomy.net
 */
@RestController
@RequestMapping(path = CorewebservicesRouteConstants.URI_PREFIX)
public class UsersController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserCreationDtoToUserModelMapper userCreationDtoToUserModelMapper;

    @Autowired
    private UserModelToDtoMapper userModelToDtoMapper;

    @PostMapping(path = "/users")
    public UserDto createUser(
            @RequestBody UserCreationDto userCreationDto
    ) {
        UserModel userModel = userCreationDtoToUserModelMapper.map(userCreationDto);

        UserModel savedUser = userService.save(userModel);

        return userModelToDtoMapper.map(savedUser);
    }
}
