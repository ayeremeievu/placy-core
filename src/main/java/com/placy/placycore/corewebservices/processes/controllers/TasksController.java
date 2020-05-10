package com.placy.placycore.corewebservices.processes.controllers;

import com.placy.placycore.core.processes.model.TaskModel;
import com.placy.placycore.core.processes.services.TasksService;
import com.placy.placycore.corewebservices.constants.CorewebservicesRouteConstants;
import com.placy.placycore.corewebservices.processes.dto.TaskDto;
import com.placy.placycore.corewebservices.processes.mappers.TaskModelToDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author a.yeremeiev@netconomy.net
 */
@RestController
@RequestMapping(path = CorewebservicesRouteConstants.URI_PREFIX)
public class TasksController {
    @Autowired
    private TasksService tasksService;

    @Autowired
    private TaskModelToDtoMapper taskModelToDtoMapper;

    @RequestMapping(path = "/tasks", method = RequestMethod.GET)
    public List<TaskDto> getTasks() {
        List<TaskModel> allTasks = tasksService.getAllTasks();

        return taskModelToDtoMapper.mapAll(allTasks);
    }
}
