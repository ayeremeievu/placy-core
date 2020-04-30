package com.placy.placycore.core.startuphooks.hooks;

import com.placy.placycore.core.processes.model.ProcessResourceModel;
import com.placy.placycore.core.processes.services.ProcessResourcesService;
import com.placy.placycore.core.startuphooks.PostStartupHook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * @author a.yeremeiev@netconomy.net
 */
public class ProcessDefinitionsProcessorHook implements PostStartupHook {
    @Autowired
    private ProcessResourcesService processResourcesService;

    @Override
    public Object run(ApplicationContext applicationContext) {
        List<ProcessResourceModel> allUnprocessedProcessResources = processResourcesService.getAllUnprocessedProcessResources();

        return processResourcesService.processResources(allUnprocessedProcessResources);
    }
}
