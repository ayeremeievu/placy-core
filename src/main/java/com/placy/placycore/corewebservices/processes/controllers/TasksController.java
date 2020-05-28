package com.placy.placycore.corewebservices.processes.controllers;

import com.placy.placycore.core.processes.data.RunTaskData;
import com.placy.placycore.core.processes.model.TaskModel;
import com.placy.placycore.core.processes.services.TasksService;
import com.placy.placycore.corewebservices.constants.CorewebservicesRouteConstants;
import com.placy.placycore.corewebservices.processes.dto.RunTaskDto;
import com.placy.placycore.corewebservices.processes.dto.TaskDto;
import com.placy.placycore.corewebservices.processes.mappers.RunTaskMapper;
import com.placy.placycore.corewebservices.processes.mappers.TaskModelToDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

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

    @Autowired
    private RunTaskMapper runTaskMapper;

    @RequestMapping(path = "/tasks", method = RequestMethod.GET)
    public List<TaskDto> getTasks() {
        List<TaskModel> allTasks = tasksService.getAllTasks();

        return taskModelToDtoMapper.mapAll(allTasks);
    }

    @RequestMapping(path = "/tasks/{code}", method = RequestMethod.GET)
    public TaskDto getTaskByCode(@PathVariable(name = "code") String code) {
        Optional<TaskModel> taskByCodeOptional = tasksService.getTaskByCodeOptional(code);

        return taskModelToDtoMapper.map(
            taskByCodeOptional.orElseThrow(() ->
                  new ResponseStatusException(NOT_FOUND, "Task not found for code : " + code)
            )
        );
    }

    @RequestMapping(path = "/tasks", method = RequestMethod.POST)
    public void runTask(@RequestBody RunTaskDto runTaskDto) {
        RunTaskData runTaskData = runTaskMapper.runTaskDtoToData(runTaskDto);
    }
}
