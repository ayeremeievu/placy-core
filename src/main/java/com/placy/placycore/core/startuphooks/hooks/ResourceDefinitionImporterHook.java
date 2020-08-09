package com.placy.placycore.core.startuphooks.hooks;

import com.placy.placycore.core.processes.model.ResourceImportModel;
import com.placy.placycore.core.processes.services.ResourceImportService;
import com.placy.placycore.core.processes.strategies.ResourceDefinitionProcessorStrategy;
import com.placy.placycore.core.startuphooks.PostStartupHook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ayeremeiev@netconomy.net
 */
public class ResourceDefinitionImporterHook implements PostStartupHook {

    private final static Logger LOG = LoggerFactory.getLogger(ResourceDefinitionImporterHook.class);

    @Autowired(required = false)
    private List<ResourceDefinitionProcessorStrategy> resourceDefinitionProcessorStrategies;

    @Autowired
    private ResourceImportService resourceImportService;

    @Override
    public Object run(ApplicationContext applicationContext) {
        if (resourcesModified()) {
            ResourceImportModel newResourceImport = resourceImportService.createNewResourceImport();

            reimportResources(newResourceImport);
        }

        return null;
    }

    private void reimportResources(ResourceImportModel newResourceImport) {
        resourceDefinitionProcessorStrategies
            .forEach(
                resourceDefinitionProcessorStrategy -> resourceDefinitionProcessorStrategy.importAllResources(newResourceImport)
            );
    }

    private boolean resourcesModified() {
        return resourceDefinitionProcessorStrategies.stream()
                                                    .anyMatch(ResourceDefinitionProcessorStrategy::hasModifiedResources);
    }
}
