package com.placy.placycore.core.processes.services;

import com.placy.placycore.core.processes.loaders.TaskLoader;
import com.placy.placycore.core.processes.model.ProcessModel;
import com.placy.placycore.core.processes.model.ProcessResourceModel;
import com.placy.placycore.core.processes.model.TaskModel;
import com.placy.placycore.core.processes.model.TaskResourceModel;
import com.placy.placycore.core.processes.repository.TaskResourcesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

/**
 * @author a.yeremeiev@netconomy.net
 */
public class TaskResourcesService {
    private static final Logger LOG = LoggerFactory.getLogger(TaskResourcesService.class);

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

    public Optional<TaskResourceModel> getTaskByResourceName(String resource) {
        return taskResourcesRepository.findFirstByResourceName(resource);
    }

    public Optional<TaskResourceModel> getResourceByTask(TaskModel taskModel) {
        return taskResourcesRepository.findFirstByTask(taskModel);
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
        LOG.info("Processing task : " + taskResource.getResourceName());
        String resourceValue = taskResource.getResourceValue();

        TaskModel taskModel = taskLoader.loadProcess(resourceValue);

        tasksService.save(taskModel);

        taskResource.setTask(taskModel);

        save(taskResource);

        LOG.info("Task resource with path : {} processsed", taskResource.getResourceName());

        return taskResource;
    }

    public List<TaskResourceModel> getAllResources() {
        return taskResourcesRepository.findAll();
    }

    public void removeAll(List<TaskResourceModel> obsoleteResources) {
        taskResourcesRepository.deleteAll(obsoleteResources);
    }

    public void flush() {
        taskResourcesRepository.flush();
    }
}
