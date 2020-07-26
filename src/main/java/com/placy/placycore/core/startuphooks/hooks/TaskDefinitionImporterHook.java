package com.placy.placycore.core.startuphooks.hooks;

import com.placy.placycore.core.context.PathsContext;
import com.placy.placycore.core.processes.model.ProcessModel;
import com.placy.placycore.core.processes.model.ProcessResourceModel;
import com.placy.placycore.core.processes.model.TaskModel;
import com.placy.placycore.core.processes.model.TaskResourceModel;
import com.placy.placycore.core.processes.services.TaskResourcesService;
import com.placy.placycore.core.processes.services.TasksService;
import com.placy.placycore.core.services.FileScannerService;
import com.placy.placycore.core.startuphooks.PostStartupHook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

/**
 * @author a.yeremeiev@netconomy.net
 */
public class TaskDefinitionImporterHook implements PostStartupHook {

    private static final Logger LOG = LoggerFactory.getLogger(TaskDefinitionImporterHook.class);

    private static final String TASK_DEFINITION_SUFFIX = ".task.json";
    private final static String TASK_RESOURCES_BASE_PATH = "placycore/core/processes/definitions";

    private String tasksResourcesBasePath = TASK_RESOURCES_BASE_PATH;

    @Autowired
    private TaskResourcesService tasksResourcesService;

    @Autowired
    private FileScannerService fileScannerService;

    @Autowired
    private TasksService tasksService;

    @Transactional(propagation = REQUIRES_NEW)
    @Override
    public Object run(ApplicationContext applicationContext) {
        LOG.info("TaskDefinitionImporterHook started");

        List<TaskResourceModel> taskResourceModels = new ArrayList<>();

        List<Resource> tasksResourcesFiles = getAllTaskResources(tasksResourcesBasePath);

        cleanObsoleteResources(tasksResourcesFiles);

        for (Resource taskResource : tasksResourcesFiles) {
            String resourceName = taskResource.getFilename();

            String fileData = fileScannerService.getFileData(taskResource);
            String fileChecksum = fileScannerService.getChecksum(fileData);

            Optional<TaskResourceModel> alreadyExistingResource = tasksResourcesService.getTaskByResourceName(resourceName);

            if(alreadyExistingResource.isEmpty()) {
                saveEmptyTask(resourceName, fileData, fileChecksum);
            } else if(checksumChanged(alreadyExistingResource, fileChecksum)) {
                saveChangedTask(resourceName, fileData, fileChecksum, alreadyExistingResource);
            }
        }

        tasksResourcesService.flush();

        return taskResourceModels;
    }

    private void cleanObsoleteResources(List<Resource> tasksResourcesFiles) {
        List<TaskResourceModel> presentResources = tasksResourcesService.getAllResources();

        List<TaskResourceModel> obsoleteResources = presentResources.stream()
                                                                       .filter(presentResource -> notExistsInFiles(
                                                                           presentResource, tasksResourcesFiles)
                                                                       )
                                                                       .collect(Collectors.toList());

        List<TaskModel> obsoleteTasks = obsoleteResources.stream()
                                                                .filter(taskResourceModel -> taskResourceModel.getTask() != null)
                                                                .map(TaskResourceModel::getTask)
                                                                .collect(Collectors.toList());

        tasksService.removeAll(obsoleteTasks);
        tasksResourcesService.removeAll(obsoleteResources);
    }

    private boolean notExistsInFiles(TaskResourceModel presentResource,
                                     List<Resource> processesResourcesFiles) {
        return !existInFiles(presentResource, processesResourcesFiles);
    }

    private boolean existInFiles(TaskResourceModel presentResource,
                                 List<Resource> processesResourcesFiles) {
        return processesResourcesFiles.stream()
                                      .anyMatch(resource -> resource.getFilename().equals(presentResource.getResourceName()));
    }

    private void saveChangedTask(String resourceName,
                                 String fileData,
                                 String fileChecksum,
                                 Optional<TaskResourceModel> alreadyExistingResource) {
        TaskResourceModel taskResourceModel = alreadyExistingResource.get();

        TaskModel obsoleteTask = taskResourceModel.getTask();

        taskResourceModel.setTask(null);
        populateTaskResource(taskResourceModel, resourceName, fileData, fileChecksum);
        saveTaskResource(taskResourceModel);

        cleanupTask(obsoleteTask);
    }

    private void cleanupTask(TaskModel obsoleteTask) {
        if(obsoleteTask != null) {
            tasksService.remove(obsoleteTask);
        }
    }

    private void saveEmptyTask(String resourceName, String fileData, String fileChecksum) {
        TaskResourceModel taskResourceModel = new TaskResourceModel();
        populateTaskResource(taskResourceModel, resourceName, fileData, fileChecksum);
        saveTaskResource(taskResourceModel);
    }

    private void saveTaskResource(TaskResourceModel taskResourceModel) {
        try {
            tasksResourcesService.save(taskResourceModel);
        } catch (RuntimeException ex) {
            throw new IllegalStateException(
                String.format("Exception occurred during saving of task resource with name '%s' and id '%s'",
                              taskResourceModel.getResourceName(),
                              taskResourceModel.getPk()
                )
            );
        }
        LOG.info("The task resource {} is saved", taskResourceModel.getResourceName());
    }

    private void populateTaskResource(TaskResourceModel taskResourceModel, String resourceName, String fileData, String fileChecksum) {
        taskResourceModel.setResourceName(resourceName);
        taskResourceModel.setResourceValue(fileData);
        taskResourceModel.setResourceChecksum(fileChecksum);
    }

    private boolean checksumChanged(Optional<TaskResourceModel> taskResourceModelOptional, String fileChecksum) {
        if (taskResourceModelOptional.isPresent()) {
            TaskResourceModel taskResourceModel = taskResourceModelOptional.get();

            String resourceChecksum = taskResourceModel.getResourceChecksum();

            return !resourceChecksum.equals(fileChecksum);
        } else {
            return false;
        }
    }

    private List<Resource> getAllTaskResources(String tasksResourcesBasePath) {
        return fileScannerService.getAllResourceFilesInDirectoriesNested(tasksResourcesBasePath, TASK_DEFINITION_SUFFIX);
    }

    public String getTasksResourcesBasePath() {
        return tasksResourcesBasePath;
    }

    public void setTasksResourcesBasePath(String tasksResourcesBasePath) {
        this.tasksResourcesBasePath = tasksResourcesBasePath;
    }


    public TaskResourcesService getTasksResourcesService() {
        return tasksResourcesService;
    }

    public void setTasksResourcesService(TaskResourcesService tasksResourcesService) {
        this.tasksResourcesService = tasksResourcesService;
    }

    public FileScannerService getFileScannerService() {
        return fileScannerService;
    }

    public void setFileScannerService(FileScannerService fileScannerService) {
        this.fileScannerService = fileScannerService;
    }

    public TasksService getTasksService() {
        return tasksService;
    }

    public void setTasksService(TasksService tasksService) {
        this.tasksService = tasksService;
    }
}
