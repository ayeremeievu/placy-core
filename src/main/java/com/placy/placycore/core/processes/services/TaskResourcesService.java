package com.placy.placycore.core.processes.services;

import com.placy.placycore.core.processes.loaders.TaskLoader;
import com.placy.placycore.core.processes.model.TaskModel;
import com.placy.placycore.core.processes.model.TaskResourceModel;
import com.placy.placycore.core.processes.repository.TaskResourcesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author a.yeremeiev@netconomy.net
 */
public class TaskResourcesService {
    Logger LOG = LoggerFactory.getLogger(TaskResourcesService.class);

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

        TaskModel taskModel = taskLoader.loadProcess(resourcePath);

        tasksService.save(taskModel);

        taskResource.setTask(taskModel);

        save(taskResource);

        LOG.info("Task resource with path : {} processsed", taskResource.getResource());

        return taskResource;
    }
}
