package com.placy.placycore.core.startuphooks.hooks;

import com.placy.placycore.core.processes.model.TaskResourceModel;
import com.placy.placycore.core.processes.services.TaskResourcesService;
import com.placy.placycore.core.startuphooks.PostStartupHook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * @author a.yeremeiev@netconomy.net
 */
public class TaskDefinitionsProcessorHook implements PostStartupHook {
    @Autowired
    private TaskResourcesService taskResourcesService;

    @Override
    public Object run(ApplicationContext applicationContext) {
        List<TaskResourceModel> allUnprocessedTasks = taskResourcesService.getAllUnprocessedTasks();

        return taskResourcesService.processAll(allUnprocessedTasks);
    }
}
