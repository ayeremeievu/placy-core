package com.placy.placycore.core.startuphooks.hooks;

import com.placy.placycore.core.processes.model.ProcessModel;
import com.placy.placycore.core.processes.model.TaskModel;
import com.placy.placycore.core.processes.model.TaskResourceModel;
import com.placy.placycore.core.processes.services.TaskResourcesService;
import com.placy.placycore.core.processes.services.TasksService;
import com.placy.placycore.core.startuphooks.PostStartupHook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author a.yeremeiev@netconomy.net
 */
public class TaskDefinitionsProcessorHook implements PostStartupHook {

    private final static Logger LOG = LoggerFactory.getLogger(TaskDefinitionsProcessorHook.class);

    @Autowired
    private TaskResourcesService taskResourcesService;

    @Autowired
    private TasksService tasksService;

    @Override
    public Object run(ApplicationContext applicationContext) {
        LOG.info("TaskDefinitionsProcessorHook started");

        List<TaskResourceModel> allUnprocessedTasks = taskResourcesService.getAllUnprocessedTasks();

        return taskResourcesService.processAll(allUnprocessedTasks);
    }
}
