package com.placy.placycore.core.processes.services;

import com.placy.placycore.core.processes.loaders.ProcessLoader;
import com.placy.placycore.core.processes.model.ProcessModel;
import com.placy.placycore.core.processes.model.ProcessResourceModel;
import com.placy.placycore.core.processes.repository.ProcessResourcesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author a.yeremeiev@netconomy.net
 */
public class ProcessResourcesService {
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

    public List<ProcessResourceModel> getAllUnprocessedProcessResources() {
        return processResourcesRepository.getAllByProcessNull();
    }

    public List<ProcessResourceModel> processResources(List<ProcessResourceModel> resourceModels) {
        resourceModels.forEach(this::processResource);

        return resourceModels;
    }

    public ProcessResourceModel processResource(ProcessResourceModel processResourceModel) {
        String resourcePath = processResourceModel.getResource();

        ProcessModel processModel = processLoader.loadProcess(resourcePath);

        processesService.save(processModel);

        processResourceModel.setProcess(processModel);

        save(processResourceModel);

        return processResourceModel;
    }
}
