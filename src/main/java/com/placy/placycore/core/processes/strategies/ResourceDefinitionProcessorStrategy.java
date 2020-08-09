package com.placy.placycore.core.processes.strategies;

import com.placy.placycore.core.processes.model.ResourceImportModel;
import com.placy.placycore.core.processes.model.ResourceModel;
import org.springframework.core.io.Resource;

import java.util.List;

/**
 * @author ayeremeiev@netconomy.net
 */
public interface ResourceDefinitionProcessorStrategy {
    boolean hasModifiedResources();

    boolean isResourceModified(Resource resource);

    void importAllResources(ResourceImportModel newResourceImport);
}
