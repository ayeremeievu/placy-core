package com.placy.placycore.core.processes.strategies.impl;

import com.placy.placycore.core.processes.model.ResourceImportModel;
import com.placy.placycore.core.processes.model.TaskResourceModel;
import com.placy.placycore.core.processes.services.TaskResourcesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author ayeremeiev@netconomy.net
 */
@Component
public class TaskResourceDefinitionProcessorStrategy extends AbstractResourceDefinitionProcessorStrategy<TaskResourceModel> {
    private static final String TASK_DEFINITION_SUFFIX = ".task.json";

    private static final Logger LOG = LoggerFactory.getLogger(TaskResourceDefinitionProcessorStrategy.class);

    @Autowired
    private TaskResourcesService tasksResourcesService;

    @Override
    protected List<TaskResourceModel> getAllResourceModelsOfLastVersion() {
        List<TaskResourceModel> result = new ArrayList<>();

        Optional<ResourceImportModel> lastResourceImport = getResourceImportService().getLastResourceImport();

        if(lastResourceImport.isPresent()) {
            result.addAll(lastResourceImport.get().getTaskResources());
        }

        return result;
    }

    @Override
    protected Optional<TaskResourceModel> getResourceModelByResourceNameFromLastVersion(String resourceName) {
        Optional<ResourceImportModel> lastResourceImport = getResourceImportService().getLastResourceImport();

        if(lastResourceImport.isPresent()) {
            ResourceImportModel resourceImportModel = lastResourceImport.get();

            return resourceImportModel.getTaskResources().stream()
                               .filter(taskResourceModel -> taskResourceModel.getResourceName().equals(resourceName))
                               .findFirst();
        }

        return Optional.empty();
    }

    @Override
    protected void saveResource(String resourceName, String fileData, String fileChecksum, ResourceImportModel newResourceImport) {
        TaskResourceModel taskResourceModel = new TaskResourceModel();
        populateTaskResource(taskResourceModel, resourceName, fileData, fileChecksum);
        taskResourceModel.setResourceImport(newResourceImport);
        saveTaskResource(taskResourceModel);
    }

    private void saveTaskResource(TaskResourceModel taskResourceModel) {
        try {
            taskResourceModel.setLatestDateImported(new Date());
            tasksResourcesService.save(taskResourceModel);
        } catch (RuntimeException ex) {
            throw new IllegalStateException(
                String.format("Exception occurred during saving of task resource with name '%s' and id '%s'",
                              taskResourceModel.getResourceName(),
                              taskResourceModel.getPk()
                ), ex
            );
        }
        LOG.info("The task resource {} is saved", taskResourceModel.getResourceName());
    }

    private void populateTaskResource(TaskResourceModel taskResourceModel, String resourceName, String fileData, String fileChecksum) {
        taskResourceModel.setResourceName(resourceName);
        taskResourceModel.setResourceContent(fileData);
        taskResourceModel.setResourceChecksum(fileChecksum);
    }

    @Override
    protected String getResourceDefinitionSuffix() {
        return TASK_DEFINITION_SUFFIX;
    }

    public TaskResourcesService getTasksResourcesService() {
        return tasksResourcesService;
    }

    public void setTasksResourcesService(TaskResourcesService tasksResourcesService) {
        this.tasksResourcesService = tasksResourcesService;
    }
}
