package com.placy.placycore.core.startuphooks.hooks;

import com.placy.placycore.core.processes.model.ProcessModel;
import com.placy.placycore.core.processes.model.ProcessResourceModel;
import com.placy.placycore.core.processes.services.ProcessResourcesService;
import com.placy.placycore.core.processes.services.ProcessesService;
import com.placy.placycore.core.services.FileScannerService;
import com.placy.placycore.core.startuphooks.PostStartupHook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

/**
 * @author a.yeremeiev@netconomy.net
 */
public class ProcessDefinitionImporterHook implements PostStartupHook {
    Logger LOG = LoggerFactory.getLogger(ProcessDefinitionsProcessorHook.class);

    private static final String PROCESS_DEFINITION_SUFFIX = ".process.json";
    private final static String PROCESS_RESOURCES_BASE_PATH = "placycore/core/processes/definitions";

    private String processesResourcesBasePath = PROCESS_RESOURCES_BASE_PATH;

    @Autowired
    private FileScannerService fileScannerService;

    @Autowired
    private ProcessResourcesService processResourcesService;

    @Autowired
    private ProcessesService processesService;

    @Override
    public Object run(ApplicationContext applicationContext) {
        LOG.info("ProcessDefinitionImporterHook started");

        List<ProcessResourceModel> processResourceModels = new ArrayList<>();

        List<Resource> processesResourcesFiles = getAllProcessesResources(processesResourcesBasePath);

        cleanObsoleteResources(processesResourcesFiles);

        for (Resource processesResource : processesResourcesFiles) {
            String resourceName = processesResource.getFilename();

            String fileData = fileScannerService.getFileData(processesResource);
            String fileChecksum = fileScannerService.getChecksum(fileData);

            Optional<ProcessResourceModel> alreadyExistingResource =
                processResourcesService.getProcessResourceByResourceName(resourceName);

            if(alreadyExistingResource.isEmpty()) {
                saveEmptyResource(resourceName, fileData, fileChecksum);
            } else if(checksumChanged(alreadyExistingResource, fileChecksum)) {
                saveChangedResource(resourceName, fileData, fileChecksum, alreadyExistingResource);
            }
        }

        processResourcesService.flush();

        return processResourceModels;
    }

    private void cleanObsoleteResources(List<Resource> processesResourcesFiles) {
        List<ProcessResourceModel> presentResources = processResourcesService.getAllResources();

        List<ProcessResourceModel> obsoleteResources = presentResources.stream()
                                                             .filter(presentResource -> notExistsInFiles(
                                                                 presentResource, processesResourcesFiles)
                                                             )
                                                             .collect(Collectors.toList());
        List<ProcessModel> obsoleteProcesses = obsoleteResources.stream()
                                                                .filter(processResourceModel -> processResourceModel.getProcess() != null)
                                                                .map(ProcessResourceModel::getProcess)
                                                                .collect(Collectors.toList());

        processResourcesService.removeAll(obsoleteResources);
        processesService.removeAll(obsoleteProcesses);
    }

    private boolean notExistsInFiles(ProcessResourceModel presentResource,
                                     List<Resource> processesResourcesFiles) {
        return !existInFiles(presentResource, processesResourcesFiles);
    }

    private boolean existInFiles(ProcessResourceModel presentResource,
                                 List<Resource> processesResourcesFiles) {
        return processesResourcesFiles.stream()
            .anyMatch(resource -> resource.getFilename().equals(presentResource.getResourceName()));
    }

    private void saveChangedResource(String resourceName,
                                     String fileData,
                                     String fileChecksum,
                                     Optional<ProcessResourceModel> alreadyExistingResource) {
        ProcessResourceModel processResourceModel = alreadyExistingResource.get();

        ProcessModel obsoleteProcess = processResourceModel.getProcess();

        processResourceModel.setProcess(null);
        populateResource(processResourceModel, resourceName, fileData, fileChecksum);
        saveResource(processResourceModel);

        cleanupProcess(obsoleteProcess);
    }

    private void cleanupProcess(ProcessModel obsoleteProcess) {
        if(obsoleteProcess != null) {
            processesService.remove(obsoleteProcess);
        }
    }

    private void saveEmptyResource(String resourceName, String fileData, String fileChecksum) {
        ProcessResourceModel processResourceModel = new ProcessResourceModel();

        populateResource(processResourceModel, resourceName, fileData, fileChecksum);
        saveResource(processResourceModel);
    }

    private void populateResource(ProcessResourceModel processResourceModel, String resourceName, String fileData, String fileChecksum) {
        processResourceModel.setResourceName(resourceName);
        processResourceModel.setResourceValue(fileData);
        processResourceModel.setResourceChecksum(fileChecksum);
    }

    private void saveResource(ProcessResourceModel processResourceModel) {
        try {
            processResourcesService.saveAndFlush(processResourceModel);
        } catch (RuntimeException ex) {
            throw new IllegalStateException(
                String.format("Exception occurred during saving of process resource with name '%s' and id '%s'",
                              processResourceModel.getResourceName(),
                              processResourceModel.getPk()
                )
            );
        }

        LOG.info("The process resource {} is saved", processResourceModel.getResourceName());
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

    public ProcessesService getProcessesService() {
        return processesService;
    }

    public void setProcessesService(ProcessesService processesService) {
        this.processesService = processesService;
    }
}
