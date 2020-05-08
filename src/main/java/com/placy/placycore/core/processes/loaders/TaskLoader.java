package com.placy.placycore.core.processes.loaders;

import com.placy.placycore.core.processes.data.ProcessDefinition;
import com.placy.placycore.core.processes.data.TaskDefinition;
import com.placy.placycore.core.processes.model.ProcessModel;
import com.placy.placycore.core.processes.model.TaskModel;
import com.placy.placycore.core.processes.populators.TaskDefinitionToModelPopulator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Component
public class TaskLoader {
    Logger LOG = LoggerFactory.getLogger(TaskLoader.class);

    @Autowired
    private TaskReader taskReader;

    @Autowired
    private TaskDefinitionToModelPopulator taskDefinitionToModelPopulator;

    public TaskModel loadProcess(String filepath) {
        LOG.info("Loading task : " + filepath);

        TaskDefinition taskDefinition = taskReader.readTask(filepath);

        TaskModel taskModel = new TaskModel();

        taskDefinitionToModelPopulator.populate(taskDefinition, taskModel);

        return taskModel;
    }
}
