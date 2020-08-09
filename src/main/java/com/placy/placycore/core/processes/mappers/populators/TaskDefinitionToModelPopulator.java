package com.placy.placycore.core.processes.mappers.populators;

import com.placy.placycore.core.populators.Populator;
import com.placy.placycore.core.processes.data.ParamDefinition;
import com.placy.placycore.core.processes.data.TaskDefinition;
import com.placy.placycore.core.processes.model.TaskModel;
import com.placy.placycore.core.processes.model.TaskParameterModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Component
public class TaskDefinitionToModelPopulator implements Populator<TaskDefinition, TaskModel> {
    @Autowired
    private ParamDefinitionToTaskParamModelPopulator paramDefinitionToTaskParamModelPopulator;

    @Override
    public void populate(TaskDefinition taskDefinition, TaskModel taskModel) {
        taskModel.setCode(taskDefinition.getCode());
        taskModel.setRunnerBean(taskDefinition.getRunnerBean());
        taskModel.setName(taskDefinition.getName());
        taskModel.setParams(getTaskParameters(taskDefinition, taskModel));
    }

    private List<TaskParameterModel> getTaskParameters(TaskDefinition taskDefinition, TaskModel taskModel) {
        List<TaskParameterModel> taskParameters = new ArrayList<>();
        List<ParamDefinition> params = taskDefinition.getParams();

        if(params == null) {
            params = new ArrayList<>();
        }

        params.stream().map(paramDefinition -> {
            TaskParameterModel parameterModel = findParam(taskModel, paramDefinition.getCode());

            if(parameterModel == null) {
                parameterModel = new TaskParameterModel();
            }

            paramDefinitionToTaskParamModelPopulator.populate(paramDefinition, parameterModel);

            parameterModel.setTask(taskModel);

            return parameterModel;
        }).forEach(taskParameters::add);
        return taskParameters;
    }

    private TaskParameterModel findParam(TaskModel taskModel, String code) {
        TaskParameterModel result = null;

        List<TaskParameterModel> params = taskModel.getParams();

        if(params != null) {
            for (TaskParameterModel param : params) {
                if(param.getCode().equals(code)) {
                    result = param;
                }
            }
        }

        return result;
    }
}
