package com.placy.placycore.core.processes.services;

import com.placy.placycore.core.processes.loaders.TaskLoader;
import com.placy.placycore.core.processes.model.TaskModel;
import com.placy.placycore.core.processes.model.TaskResourceModel;
import com.placy.placycore.core.processes.repository.TaskResourcesRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author a.yeremeiev@netconomy.net
 */
public class TaskResourcesService {

    @Autowired
    private TaskResourcesRepository taskResourcesRepository;

    @Autowired
    private TasksService tasksService;

    @Autowired
    private TaskLoader taskLoader;

    public void save(TaskResourceModel taskResourceModel) {
        taskResourcesRepository.save(taskResourceModel);
    }

    public void saveAll(List<TaskResourceModel> taskResourceModelList) {
        taskResourcesRepository.saveAll(taskResourceModelList);
    }

    public List<TaskResourceModel> getAllUnprocessedTasks() {
        return taskResourcesRepository.findAllByTaskNull();
    }

    public Object processAll(List<TaskResourceModel> taskResources) {
        for (TaskResourceModel task : taskResources) {
            process(task);
        }
        return taskResources;
    }

    private TaskResourceModel process(TaskResourceModel taskResource) {
        String resourcePath = taskResource.getResource();

        TaskModel taskModel = taskLoader.loadProcess(this.getClass().getClassLoader(), resourcePath);

        tasksService.save(taskModel);

        taskResource.setTask(taskModel);

        save(taskResource);

        return taskResource;
    }
}
