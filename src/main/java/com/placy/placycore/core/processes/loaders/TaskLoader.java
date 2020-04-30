package com.placy.placycore.core.processes.loaders;

import com.placy.placycore.core.processes.data.ProcessDefinition;
import com.placy.placycore.core.processes.data.TaskDefinition;
import com.placy.placycore.core.processes.model.ProcessModel;
import com.placy.placycore.core.processes.model.TaskModel;
import com.placy.placycore.core.processes.populators.TaskDefinitionToModelPopulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Component
public class TaskLoader {
    @Autowired
    private TaskReader taskReader;

    @Autowired
    private TaskDefinitionToModelPopulator taskDefinitionToModelPopulator;

    public TaskModel loadProcess(ClassLoader classLoader, String filepath) {
        TaskDefinition taskDefinition = taskReader.readTask(classLoader, filepath);

        TaskModel taskModel = new TaskModel();

        taskDefinitionToModelPopulator.populate(taskDefinition, taskModel);

        return taskModel;
    }
}
