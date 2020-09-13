package com.placy.placycore.core.startuphooks.hooks;

import com.placy.placycore.core.processes.model.ProcessResourceModel;
import com.placy.placycore.core.processes.model.ResourceImportModel;
import com.placy.placycore.core.processes.model.ResourceImportResultEnum;
import com.placy.placycore.core.processes.model.TaskResourceModel;
import com.placy.placycore.core.processes.services.*;
import com.placy.placycore.core.startuphooks.PostStartupHook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author ayeremeiev@netconomy.net
 */
public class ResourceDefinitionsProcessorHook implements PostStartupHook {
    private final static Logger LOG = LoggerFactory.getLogger(ResourceDefinitionsProcessorHook.class);

    @Autowired
    private ResourceImportService resourceImportService;

    @Autowired
    private ResourceProcessorService resourceProcessorService;

    @Override
    public Object run(ApplicationContext applicationContext) {
        LOG.info("ResourceDefinitionsProcessorHook started");

        Optional<ResourceImportModel> lastResourceImport = resourceImportService.getLastResourceImport();

        if(lastResourceImport.isPresent()) {
            ResourceImportModel resourceImportModel = lastResourceImport.get();

            ResourceImportResultEnum result = resourceImportModel.getResult();

            if(result == null || result == ResourceImportResultEnum.ERROR) {
                processResources(resourceImportModel);
            }
        }

        return null;
    }

    public void processResources(ResourceImportModel resourceImportModel) {
        try {
            resourceProcessorService.processResources(resourceImportModel);

            resourceImportModel.setResult(ResourceImportResultEnum.SUCCESS);

            resourceImportService.save(resourceImportModel);
        } catch (Exception ex) {
            LOG.error("Error occurred during process of resourceImportModel with version '{}'", resourceImportModel.getVersion(), ex);

            resourceImportModel.setResult(ResourceImportResultEnum.ERROR);

            resourceImportService.save(resourceImportModel);
        }
    }


}
