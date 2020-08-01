package com.placy.placycore.core.processes.model;

import com.placy.placycore.core.model.DomainModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Entity
@Table(name = "processResources")
public class ProcessResourceModel extends DomainModel {
    @OneToOne
    @JoinColumn(name = "pr_process_pk", unique = true)
    private ProcessModel process;

    @Column(name = "pr_resource_name", nullable = false)
    private String resourceName;

    @Column(name = "pr_resource_value", nullable = false)
    private String resourceValue;

    @Column(name = "pr_resource_checksum", nullable = false)
    private String resourceChecksum;

    public ProcessModel getProcess() {
        return process;
    }

    public void setProcess(ProcessModel process) {
        this.process = process;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resource) {
        this.resourceName = resource;
    }

    public String getResourceValue() {
        return resourceValue;
    }

    public void setResourceValue(String resourceValue) {
        this.resourceValue = resourceValue;
    }

    public String getResourceChecksum() {
        return resourceChecksum;
    }

    public void setResourceChecksum(String resourceChecksum) {
        this.resourceChecksum = resourceChecksum;
    }
}
