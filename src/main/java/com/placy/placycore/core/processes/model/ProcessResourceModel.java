package com.placy.placycore.core.processes.model;

import com.placy.placycore.core.model.UuidDomainModel;

import java.util.Date;

import javax.persistence.*;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Entity
@Table(name = "processResources")
public class ProcessResourceModel extends ResourceModel {
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pr_process_pk", unique = true)
    private ProcessModel process;

    @Column(name = "pr_latest_date_imported", nullable = false)
    private Date latestDateImported;

    @Column(name = "pr_latest_date_processed", nullable = false)
    private Date latestDateProcessed;

    @ManyToOne
    @JoinColumn(name = "pr_resource_import_version", nullable = false)
    private ResourceImportModel resourceImport;

    public ProcessModel getProcess() {
        return process;
    }

    public void setProcess(ProcessModel process) {
        this.process = process;
    }

    public Date getLatestDateImported() {
        return latestDateImported;
    }

    public void setLatestDateImported(Date lastDateImported) {
        this.latestDateImported = lastDateImported;
    }

    public Date getLatestDateProcessed() {
        return latestDateProcessed;
    }

    public void setLatestDateProcessed(Date lastProcessedImported) {
        this.latestDateProcessed = lastProcessedImported;
    }

    public ResourceImportModel getResourceImport() {
        return resourceImport;
    }

    public void setResourceImport(ResourceImportModel resourceImport) {
        this.resourceImport = resourceImport;
    }
}
