package com.placy.placycore.core.processes.populators;

import com.placy.placycore.core.populators.Populator;
import com.placy.placycore.core.processes.data.TaskDefinition;
import com.placy.placycore.core.processes.model.TaskModel;
import org.springframework.stereotype.Component;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Component
public class TaskDefinitionToModelPopulator implements Populator<TaskDefinition, TaskModel> {

    @Override
    public void populate(TaskDefinition taskDefinition, TaskModel taskModel) {
        taskModel.setCode(taskDefinition.getCode());
    }
}
