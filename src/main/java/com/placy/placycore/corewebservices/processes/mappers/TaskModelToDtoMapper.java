package com.placy.placycore.corewebservices.processes.mappers;

import com.placy.placycore.core.processes.model.TaskModel;
import com.placy.placycore.core.mappers.AbstractSimpleMapper;
import com.placy.placycore.corewebservices.processes.dto.ParamDto;
import com.placy.placycore.corewebservices.processes.dto.TaskDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Component
public class TaskModelToDtoMapper extends AbstractSimpleMapper<TaskModel, TaskDto> {
    @Autowired
    private TaskParamModelToDtoMapper taskParamModelToDtoMapper;

    @Override
    public TaskDto map(TaskModel taskModel) {
        TaskDto taskDto = new TaskDto();

        taskDto.setCode(taskModel.getCode());
        taskDto.setName(taskModel.getName());
        taskDto.setParams(getParams(taskModel));

        return taskDto;
    }

    private List<ParamDto> getParams(TaskModel taskModel) {
        return taskParamModelToDtoMapper.mapAll(taskModel.getParams());
    }
}
