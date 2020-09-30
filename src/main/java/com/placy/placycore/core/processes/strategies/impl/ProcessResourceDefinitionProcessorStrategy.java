package com.placy.placycore.core.processes.strategies.impl;

import com.placy.placycore.core.processes.model.ProcessResourceModel;
import com.placy.placycore.core.processes.model.ResourceImportModel;
import com.placy.placycore.core.processes.services.ProcessResourcesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author ayeremeiev@netconomy.net
 */
@Service
public class ProcessResourceDefinitionProcessorStrategy extends AbstractResourceDefinitionProcessorStrategy<ProcessResourceModel> {
    private static final Logger LOG = LoggerFactory.getLogger(TaskResourceDefinitionProcessorStrategy.class);

    private static final String PROCESS_DEFINITION_SUFFIX = ".process.json";

    @Autowired
    private ProcessResourcesService processResourcesService;

    @Override
    protected List<ProcessResourceModel> getAllResourceModelsOfLastVersion() {
        List<ProcessResourceModel> result = new ArrayList<>();

        Optional<ResourceImportModel> lastResourceImport = getResourceImportService().getLastResourceImport();

        if(lastResourceImport.isPresent()) {
            ResourceImportModel resourceImportModel = lastResourceImport.get();
            resourceImportModel.getProcessResources().size();

            result.addAll(resourceImportModel.getProcessResources());
        }

        return result;
    }

    @Override
    protected Optional<ProcessResourceModel> getResourceModelByResourceNameFromLastVersion(String resourceName) {
        Optional<ResourceImportModel> lastResourceImport = getResourceImportService().getLastResourceImport();

        if(lastResourceImport.isPresent()) {
            ResourceImportModel resourceImportModel = lastResourceImport.get();

            return resourceImportModel.getProcessResources().stream()
                                      .filter(processResourceModel -> processResourceModel.getResourceName().equals(resourceName))
                                      .findFirst();
        }

        return Optional.empty();
    }

    @Override
    protected void saveResource(String resourceName, String fileData, String fileChecksum, ResourceImportModel resourceImport) {
        ProcessResourceModel processResourceModel = new ProcessResourceModel();
        populateProcessResource(processResourceModel, resourceName, fileData, fileChecksum);
        processResourceModel.setResourceImport(resourceImport);
        saveProcessResource(processResourceModel);
    }

    private void saveProcessResource(ProcessResourceModel processResourceModel) {
        try {
            processResourceModel.setLatestDateImported(new Date());
            processResourcesService.save(processResourceModel);
        } catch (RuntimeException ex) {
            throw new IllegalStateException(
                String.format("Exception occurred during saving of task resource with name '%s', id '%s', version '%d'",
                              processResourceModel.getResourceName(),
                              processResourceModel.getPk(),
                              processResourceModel.getResourceImport().getVersion()
                ), ex
            );
        }
        LOG.info("The task resource {} is saved", processResourceModel.getResourceName());
    }

    private void populateProcessResource(ProcessResourceModel processResourceModel, String resourceName, String fileData, String fileChecksum) {
        processResourceModel.setResourceName(resourceName);
        processResourceModel.setResourceContent(fileData);
        processResourceModel.setResourceChecksum(fileChecksum);
    }

    @Override
    protected String getResourceDefinitionSuffix() {
        return PROCESS_DEFINITION_SUFFIX;
    }

    public ProcessResourcesService getProcessResourcesService() {
        return processResourcesService;
    }

    public void setProcessResourcesService(ProcessResourcesService processResourcesService) {
        this.processResourcesService = processResourcesService;
    }
}
