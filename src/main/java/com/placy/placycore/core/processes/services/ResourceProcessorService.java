package com.placy.placycore.core.processes.services;

import com.placy.placycore.core.processes.model.ProcessResourceModel;
import com.placy.placycore.core.processes.model.ResourceImportModel;
import com.placy.placycore.core.processes.model.TaskResourceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class ResourceProcessorService {
    @Autowired
    private TaskResourcesService taskResourcesService;

    @Autowired
    private TasksService tasksService;

    @Autowired
    private ProcessResourcesService processResourcesService;

    public ResourceProcessorService() {
    }

    @Transactional(rollbackFor = Exception.class)
    public void processResources(ResourceImportModel resourceImportModel) {
        processTasks(resourceImportModel);
        processProcesses(resourceImportModel);
    }

    public void processProcesses(ResourceImportModel resourceImportModel) {
        List<ProcessResourceModel> processResources = processResourcesService.getAllResourcesByImport(resourceImportModel);
        processResourcesService.processResources(processResources);
    }

    public void processTasks(ResourceImportModel resourceImportModel) {
        List<TaskResourceModel> taskResources = taskResourcesService.getAllResourcesByImport(resourceImportModel);
        taskResourcesService.processAll(taskResources);
    }
}
