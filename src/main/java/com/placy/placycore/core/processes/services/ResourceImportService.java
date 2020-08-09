package com.placy.placycore.core.processes.services;

import com.placy.placycore.core.processes.model.ResourceImportModel;
import com.placy.placycore.core.processes.repository.ResourceImportRepository;
import com.placy.placycore.core.services.AbstractModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author ayeremeiev@netconomy.net
 */
@Component
public class ResourceImportService extends AbstractModelService<ResourceImportModel, String> {
    @Autowired
    private ResourceImportRepository resourceImportRepository;

    public Optional<ResourceImportModel> getLastResourceImport() {
        return resourceImportRepository.findTop1ByOrderByVersionDesc();
    }

    public ResourceImportModel createNewResourceImport() {
        ResourceImportModel resourceImportModel = new ResourceImportModel();

        return save(resourceImportModel);
    }

    @Override
    public JpaRepository<ResourceImportModel, String> getRepository() {
        return resourceImportRepository;
    }

    public ResourceImportRepository getResourceImportRepository() {
        return resourceImportRepository;
    }

    public void setResourceImportRepository(ResourceImportRepository resourceImportRepository) {
        this.resourceImportRepository = resourceImportRepository;
    }
}
