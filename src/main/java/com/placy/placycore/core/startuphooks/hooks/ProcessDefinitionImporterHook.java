package com.placy.placycore.core.startuphooks.hooks;

import com.placy.placycore.core.processes.model.ProcessResourceModel;
import com.placy.placycore.core.processes.model.TaskResourceModel;
import com.placy.placycore.core.processes.services.ProcessResourcesService;
import com.placy.placycore.core.services.FileScannerService;
import com.placy.placycore.core.startuphooks.PostStartupHook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author a.yeremeiev@netconomy.net
 */
public class ProcessDefinitionImporterHook implements PostStartupHook {
    Logger LOG = LoggerFactory.getLogger(ProcessDefinitionsProcessorHook.class);
    private static final String PROCESS_DEFINITION_SUFFIX = ".process.json";

    private String processesResourcesBasePath;

    private FileScannerService fileScannerService;

    private ProcessResourcesService processResourcesService;

    @Override
    public Object run(ApplicationContext applicationContext) {
        LOG.info("ProcessDefinitionImporterHook started");

        List<ProcessResourceModel> processResourceModels = new ArrayList<>();

        List<Resource> processesResourcesFiles = getAllProcessesResources(processesResourcesBasePath);

        for (Resource processesResource : processesResourcesFiles) {
            String resourceName = processesResource.getFilename();

            String fileData = fileScannerService.getFileData(processesResource);
            String fileChecksum = fileScannerService.getChecksum(fileData);

            Optional<ProcessResourceModel> alreadyExistingResource =
                processResourcesService.getProcessResourceByResourceName(resourceName);

            if(alreadyExistingResource.isEmpty() || checksumChanged(alreadyExistingResource, fileChecksum)) {
                ProcessResourceModel processResourceModel = new ProcessResourceModel();

                processResourceModel.setResourceName(resourceName);
                processResourceModel.setResourceValue(fileData);
                processResourceModel.setResourceChecksum(fileChecksum);

                processResourcesService.save(processResourceModel);

                LOG.info("The process resource {} is added", processResourceModel.getResourceName());
            }
        }

        return processResourceModels;
    }

    private boolean checksumChanged(Optional<ProcessResourceModel> alreadyExistingResourceOptional, String fileChecksum) {
        if(alreadyExistingResourceOptional.isPresent()) {
            ProcessResourceModel processResourceModel = alreadyExistingResourceOptional.get();

            String resourceChecksum = processResourceModel.getResourceChecksum();

            return !resourceChecksum.equals(fileChecksum);
        } else {
            return false;
        }
    }

    private List<Resource> getAllProcessesResources(String processesResourcesBasePath) {
        return fileScannerService.getAllResourceFilesInDirectoriesNested(processesResourcesBasePath, PROCESS_DEFINITION_SUFFIX);
    }

    public String getProcessesResourcesBasePath() {
        return processesResourcesBasePath;
    }

    public void setProcessesResourcesBasePath(String processesResourcesBasePath) {
        this.processesResourcesBasePath = processesResourcesBasePath;
    }

    public ProcessResourcesService getProcessResourcesService() {
        return processResourcesService;
    }

    public void setProcessResourcesService(ProcessResourcesService processResourcesService) {
        this.processResourcesService = processResourcesService;
    }

    public FileScannerService getFileScannerService() {
        return fileScannerService;
    }

    public void setFileScannerService(FileScannerService fileScannerService) {
        this.fileScannerService = fileScannerService;
    }
}
