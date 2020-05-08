package com.placy.placycore.core.processes.populators;

import com.placy.placycore.core.populators.Populator;
import com.placy.placycore.core.processes.data.ParamDefinition;
import com.placy.placycore.core.processes.data.TaskParamDefinitionInfo;
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
        params.stream().map(paramDefinition -> {
            TaskParameterModel parameterModel = new TaskParameterModel();

            paramDefinitionToTaskParamModelPopulator.populate(paramDefinition, parameterModel);

            parameterModel.setTask(taskModel);

            return parameterModel;
        }).forEach(taskParameters::add);
        return taskParameters;
    }
}
