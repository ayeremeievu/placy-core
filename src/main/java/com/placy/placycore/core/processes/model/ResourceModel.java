package com.placy.placycore.core.processes.model;

import com.placy.placycore.core.model.UuidDomainModel;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

/**
 * @author ayeremeiev@netconomy.net
 */
@MappedSuperclass
public abstract class ResourceModel extends UuidDomainModel {
    @Column(name = "r_resource_name", nullable = false)
    private String resourceName;

    @Column(name = "r_resource_content", nullable = false)
    private String resourceContent;

    @Column(name = "r_resource_checksum", nullable = false)
    private String resourceChecksum;

    public ResourceModel() {
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceContent() {
        return resourceContent;
    }

    public void setResourceContent(String resourceContent) {
        this.resourceContent = resourceContent;
    }

    public String getResourceChecksum() {
        return resourceChecksum;
    }

    public void setResourceChecksum(String resourceChecksum) {
        this.resourceChecksum = resourceChecksum;
    }
}
