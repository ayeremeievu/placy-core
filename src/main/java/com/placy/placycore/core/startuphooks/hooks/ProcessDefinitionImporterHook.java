package com.placy.placycore.core.startuphooks.hooks;

import com.placy.placycore.core.processes.model.ProcessResourceModel;
import com.placy.placycore.core.processes.services.ProcessResourcesService;
import com.placy.placycore.core.startuphooks.PostStartupHook;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @author a.yeremeiev@netconomy.net
 */
public class ProcessDefinitionImporterHook implements PostStartupHook {
    private List<String> processesResourcesPaths;

    private ProcessResourcesService processResourcesService;

    @Override
    public Object run(ApplicationContext applicationContext) {
        List<ProcessResourceModel> processResourceModels = new ArrayList<>();

        for (String processResourcesPath : processesResourcesPaths) {
            ProcessResourceModel processResourceModel = new ProcessResourceModel();

            processResourceModel.setResource(processResourcesPath);

            processResourceModels.add(processResourceModel);
        }

        processResourcesService.saveAll(processResourceModels);

        return processResourceModels;
    }

    public List<String> getProcessesResourcesPaths() {
        return processesResourcesPaths;
    }

    public void setProcessesResourcesPaths(List<String> processesResourcesPaths) {
        this.processesResourcesPaths = processesResourcesPaths;
    }

    public ProcessResourcesService getProcessResourcesService() {
        return processResourcesService;
    }

    public void setProcessResourcesService(ProcessResourcesService processResourcesService) {
        this.processResourcesService = processResourcesService;
    }
}
