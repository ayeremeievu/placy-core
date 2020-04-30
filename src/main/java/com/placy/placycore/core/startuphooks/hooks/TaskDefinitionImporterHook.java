package com.placy.placycore.core.startuphooks.hooks;

import com.placy.placycore.core.processes.model.ProcessResourceModel;
import com.placy.placycore.core.processes.model.TaskResourceModel;
import com.placy.placycore.core.processes.services.TaskResourcesService;
import com.placy.placycore.core.processes.services.TasksService;
import com.placy.placycore.core.startuphooks.PostStartupHook;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @author a.yeremeiev@netconomy.net
 */
public class TaskDefinitionImporterHook implements PostStartupHook {
    private List<String> tasksResourcesPaths;

    private TaskResourcesService tasksResourcesService;

    @Override
    public Object run(ApplicationContext applicationContext) {
        List<TaskResourceModel> taskResourceModels = new ArrayList<>();

        for (String taskResourcesPath : tasksResourcesPaths) {
            TaskResourceModel taskResourceModel = new TaskResourceModel();

            taskResourceModel.setResource(taskResourcesPath);

            taskResourceModels.add(taskResourceModel);
        }

        tasksResourcesService.saveAll(taskResourceModels);

        return taskResourceModels;
    }

    public List<String> getTasksResourcesPaths() {
        return tasksResourcesPaths;
    }

    public void setTasksResourcesPaths(List<String> tasksResourcesPaths) {
        this.tasksResourcesPaths = tasksResourcesPaths;
    }

    public TaskResourcesService getTasksResourcesService() {
        return tasksResourcesService;
    }

    public void setTasksResourcesService(TaskResourcesService tasksResourcesService) {
        this.tasksResourcesService = tasksResourcesService;
    }
}
