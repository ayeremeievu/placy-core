package com.placy.placycore.core.processes.strategies.impl;

import com.placy.placycore.core.processes.model.ResourceImportModel;
import com.placy.placycore.core.processes.model.ResourceModel;
import com.placy.placycore.core.processes.services.ResourceImportService;
import com.placy.placycore.core.processes.strategies.ResourceDefinitionProcessorStrategy;
import com.placy.placycore.core.services.FileScannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ayeremeiev@netconomy.net
 */
public abstract class AbstractResourceDefinitionProcessorStrategy<T extends ResourceModel> implements ResourceDefinitionProcessorStrategy {
    private final static String RESOURCES_BASE_PATH = "placycore/core/processes/definitions";

    @Autowired
    private FileScannerService fileScannerService;

    @Autowired
    private ResourceImportService resourceImportService;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean hasModifiedResources() {
        List<Resource> resourcesDefinitions = getAllResourcesDefinitions();

        validateResources(resourcesDefinitions);

        List<T> allResourceModels = getAllResourceModelsOfLastVersion();

        if(isInitialEmpty(resourcesDefinitions, allResourceModels)) {
            return false;
        }

        if(existsDeletedResource(resourcesDefinitions, allResourceModels)) {
            return true;
        }

        for (Resource resourcesDefinition : resourcesDefinitions) {
            if(isResourceModified(resourcesDefinition)) {
                return true;
            }
        }

        return false;
    }

    private void validateResources(List<Resource> resourcesDefinitions) {
        List<String> dublicates = getDublicates(resourcesDefinitions);

        if(!dublicates.isEmpty()) {
            throw new IllegalStateException("There are dublicates in resource definitions found : " + dublicates);
        }
    }

    private List<String> getDublicates(List<Resource> resourcesDefinitions) {
        List<String> dublicates = new ArrayList<>();

        Set<String> uniqueResourceNames = new HashSet<>();

        List<String> resourceNames = resourcesDefinitions.stream()
                                                   .map(resource -> resource.getFilename())
                                                   .collect(Collectors.toList());

        for (String resourceName : resourceNames) {
            int oldSize = uniqueResourceNames.size();

            uniqueResourceNames.add(resourceName);

            if(oldSize == uniqueResourceNames.size()) {
                dublicates.add(resourceName);
            }
        }

        return dublicates;
    }

    public boolean isInitialEmpty(List<Resource> resourcesDefinitions, List<T> allResourceModels) {
        return (resourcesDefinitions == null || resourcesDefinitions.isEmpty()) &&
            (allResourceModels == null || allResourceModels.isEmpty());
    }

    protected boolean existsDeletedResource(List<Resource> resourcesDefinitions, List<T> allResourceModels) {
        // At least one case when resource definition doesn't contain resource
        return allResourceModels.stream()
                            .anyMatch(resourceModel -> !containsByName(resourcesDefinitions, resourceModel));
    }

    private boolean containsByName(List<Resource> resourcesDefinitions, ResourceModel resourceModel) {
        return resourcesDefinitions.stream()
                                   .anyMatch(resourcesDefinition -> resourceModel.equals(resourceModel));
    }

    protected abstract List<T> getAllResourceModelsOfLastVersion();

    @Override
    public boolean isResourceModified(Resource resource) {
        String resourceName = resource.getFilename();

        String fileData = fileScannerService.getFileData(resource);
        String fileChecksum = fileScannerService.getChecksum(fileData);

        Optional<? extends ResourceModel> alreadyExistingResource = getResourceModelByResourceNameFromLastVersion(resourceName);

        return !alreadyExistingResource.isPresent() || checksumChanged(alreadyExistingResource, fileChecksum);
    }

    protected boolean checksumChanged(Optional<? extends ResourceModel> resourceModelOptional, String fileChecksum) {
        if(!resourceModelOptional.isPresent()) {
            throw new IllegalArgumentException("Resource cannot be null");
        }

        ResourceModel resourceModel = resourceModelOptional.get();

        String resourceChecksum = resourceModel.getResourceChecksum();

        return !resourceChecksum.equals(fileChecksum);
    }

    protected abstract Optional<T> getResourceModelByResourceNameFromLastVersion(String resourceName);

    @Override
    public void importAllResources(ResourceImportModel newResourceImport) {
        List<Resource> resourcesDefinitions = getAllResourcesDefinitions();

        for (Resource resourcesDefinition : resourcesDefinitions) {
            importResource(resourcesDefinition, newResourceImport);
        }
    }

    private void importResource(Resource resourcesDefinition, ResourceImportModel newResourceImport) {
        String resourceName = resourcesDefinition.getFilename();

        String fileData = fileScannerService.getFileData(resourcesDefinition);
        String fileChecksum = fileScannerService.getChecksum(fileData);

        saveResource(resourceName, fileData, fileChecksum, newResourceImport);
    }

    protected abstract void saveResource(String resourceName, String fileData, String fileChecksum, ResourceImportModel newResourceImport);

    protected List<Resource> getAllResourcesDefinitions() {
        return fileScannerService.getAllResourceFilesInDirectoriesNested(getResourcesBasePath(), getResourceDefinitionSuffix());
    }

    protected String getResourcesBasePath() {
        return RESOURCES_BASE_PATH;
    }

    protected abstract String getResourceDefinitionSuffix();

    public FileScannerService getFileScannerService() {
        return fileScannerService;
    }

    public void setFileScannerService(FileScannerService fileScannerService) {
        this.fileScannerService = fileScannerService;
    }

    public ResourceImportService getResourceImportService() {
        return resourceImportService;
    }

    public void setResourceImportService(ResourceImportService resourceImportService) {
        this.resourceImportService = resourceImportService;
    }
}
