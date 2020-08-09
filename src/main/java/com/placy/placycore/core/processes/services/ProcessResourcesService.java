package com.placy.placycore.core.processes.services;

import com.placy.placycore.core.processes.loaders.ProcessLoader;
import com.placy.placycore.core.processes.model.ProcessModel;
import com.placy.placycore.core.processes.model.ProcessResourceModel;
import com.placy.placycore.core.processes.model.ResourceImportModel;
import com.placy.placycore.core.processes.repository.ProcessResourcesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author a.yeremeiev@netconomy.net
 */
public class ProcessResourcesService {
    private final static Logger LOG = LoggerFactory.getLogger(ProcessResourcesService.class);
    @Autowired
    private ProcessResourcesRepository processResourcesRepository;

    @Autowired
    private ProcessesService processesService;

    @Autowired
    private ProcessLoader processLoader;

    public void save(ProcessResourceModel model) {
        processResourcesRepository.save(model);
    }

    public void saveAll(List<ProcessResourceModel> models) {
        processResourcesRepository.saveAll(models);
    }

    public Optional<ProcessResourceModel> getProcessResourceByResourceName(String resource) {
        return processResourcesRepository.getFirstByResourceName(resource);
    }

    public List<ProcessResourceModel> getAllUnprocessedProcessResources() {
        return processResourcesRepository.getAllProcessesToProcess();
    }

    public List<ProcessResourceModel> processResources(List<ProcessResourceModel> resourceModels) {
        resourceModels.forEach(this::processResource);

        return resourceModels;
    }

    public Optional<ProcessResourceModel> getResourceByProcess(ProcessModel processModel) {
        return processResourcesRepository.getFirstByProcess(processModel);
    }

    public ProcessResourceModel processResource(ProcessResourceModel processResourceModel) {
        LOG.info("Processing process : " + processResourceModel.getResourceName());
        String resourceContent = processResourceModel.getResourceContent();

        ProcessModel processToSave;

        if(processResourceModel.getProcess() != null) {
            processToSave = processResourceModel.getProcess();
        } else {
            processToSave = new ProcessModel();
            processResourceModel.setProcess(processToSave);
        }

        ProcessModel loadedProcessModel = processLoader.loadProcess(resourceContent, processToSave);

        processesService.save(loadedProcessModel);

        processResourceModel.setLatestDateProcessed(new Date());
        save(processResourceModel);

        return processResourceModel;
    }

    public List<ProcessResourceModel> getAllResources() {
        return processResourcesRepository.findAll();
    }

    public List<ProcessResourceModel> getAllResourcesByImport(ResourceImportModel resourceImportModel) {
        return processResourcesRepository.getAllByResourceImport(resourceImportModel);
    }

    public void removeAll(List<ProcessResourceModel> processResourceModels) {
        processResourcesRepository.deleteAll(processResourceModels);
    }

    public void flush() {
        processResourcesRepository.flush();
    }

    public void saveAndFlush(ProcessResourceModel processResourceModel) {
        processResourcesRepository.saveAndFlush(processResourceModel);
    }
}
