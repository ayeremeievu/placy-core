package com.placy.placycore.core.processes.loaders;

import com.placy.placycore.core.processes.data.TaskDefinition;
import com.placy.placycore.core.processes.exceptions.ExecutiveEntityLoadingException;
import com.placy.placycore.core.processes.model.TaskModel;
import com.placy.placycore.core.processes.mappers.populators.TaskDefinitionToModelPopulator;
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
        TaskModel taskModel = new TaskModel();

        try {
            TaskDefinition taskDefinition = taskReader.readTask(filepath);

            taskDefinitionToModelPopulator.populate(taskDefinition, taskModel);
        } catch (RuntimeException ex) {
            throw new ExecutiveEntityLoadingException("Error occurred during task loading process", ex);
        }

        return taskModel;
    }
}
