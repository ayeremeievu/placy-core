package com.placy.placycore.core.processes.services;

import com.placy.placycore.core.processes.loaders.ProcessLoader;
import com.placy.placycore.core.processes.model.ProcessModel;
import com.placy.placycore.core.processes.model.ProcessResourceModel;
import com.placy.placycore.core.processes.repository.ProcessResourcesRepository;
import com.placy.placycore.core.startuphooks.hooks.ProcessDefinitionsProcessorHook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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
        return processResourcesRepository.getAllByProcessNull();
    }

    public List<ProcessResourceModel> processResources(List<ProcessResourceModel> resourceModels) {
        resourceModels.forEach(this::processResource);

        return resourceModels;
    }

    public ProcessResourceModel processResource(ProcessResourceModel processResourceModel) {
        LOG.info("Processing process : " + processResourceModel.getResourceName());
        String resourceValue = processResourceModel.getResourceValue();

        ProcessModel processModel = processLoader.loadProcess(resourceValue);

        processesService.save(processModel);

        processResourceModel.setProcess(processModel);

        save(processResourceModel);

        return processResourceModel;
    }
}
