package com.placy.placycore.core.startuphooks.hooks;

import com.placy.placycore.core.context.PathsContext;
import com.placy.placycore.core.processes.model.TaskResourceModel;
import com.placy.placycore.core.processes.services.TaskResourcesService;
import com.placy.placycore.core.services.FileScannerService;
import com.placy.placycore.core.startuphooks.PostStartupHook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author a.yeremeiev@netconomy.net
 */
public class TaskDefinitionImporterHook implements PostStartupHook {
    private static final Logger LOG = LoggerFactory.getLogger(TaskDefinitionImporterHook.class);
    private static final String TASK_DEFINITION_SUFFIX = ".task.json";

    private String tasksResourcesBasePath;

    private PathsContext pathsContext;

    private TaskResourcesService tasksResourcesService;

    private FileScannerService fileScannerService;

    @Override
    public Object run(ApplicationContext applicationContext) {
        LOG.info("TaskDefinitionImporterHook started");

        List<TaskResourceModel> taskResourceModels = new ArrayList<>();

        List<Resource> tasksResourcesFiles = getAllTaskResources(tasksResourcesBasePath);

        for (Resource taskResource : tasksResourcesFiles) {
            String resourceName = taskResource.getFilename();

            String fileData = fileScannerService.getFileData(taskResource);
            String fileChecksum = fileScannerService.getChecksum(fileData);

            Optional<TaskResourceModel> alreadyExistingResource = tasksResourcesService.getTaskByResourceName(resourceName);

            if(alreadyExistingResource.isEmpty() || checksumChanged(alreadyExistingResource, fileChecksum)) {
                TaskResourceModel taskResourceModel = new TaskResourceModel();

                taskResourceModel.setResourceName(resourceName);
                taskResourceModel.setResourceValue(fileData);
                taskResourceModel.setResourceChecksum(fileChecksum);

                tasksResourcesService.save(taskResourceModel);
                LOG.info("The task resource {} is added", taskResourceModel.getResourceName());
            }
        }


        return taskResourceModels;
    }

    private boolean checksumChanged(Optional<TaskResourceModel> taskResourceModelOptional, String fileChecksum) {
        if(taskResourceModelOptional.isPresent()) {
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

    public PathsContext getPathsContext() {
        return pathsContext;
    }

    public void setPathsContext(PathsContext pathsContext) {
        this.pathsContext = pathsContext;
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
}
