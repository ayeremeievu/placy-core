package com.placy.placycore.core.processes.model;

import com.placy.placycore.core.model.UuidDomainModel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Entity
@Table(name = "taskResources")
public class TaskResourceModel extends ResourceModel {
    @OneToOne
    @JoinColumn(name = "tr_task_pk", unique = true)
    private TaskModel task;

    @Column(name = "tr_latest_date_imported", nullable = false)
    private Date latestDateImported;

    @Column(name = "tr_latest_date_processed", nullable = false)
    private Date latestDateProcessed;

    @ManyToOne
    @JoinColumn(name = "tr_resource_import_version", nullable = false)
    private ResourceImportModel resourceImport;

    public TaskModel getTask() {
        return task;
    }

    public void setTask(TaskModel task) {
        this.task = task;
    }

    public Date getLatestDateImported() {
        return latestDateImported;
    }

    public void setLatestDateImported(Date latestDateImported) {
        this.latestDateImported = latestDateImported;
    }

    public Date getLatestDateProcessed() {
        return latestDateProcessed;
    }

    public void setLatestDateProcessed(Date latestDateProcessed) {
        this.latestDateProcessed = latestDateProcessed;
    }

    public ResourceImportModel getResourceImport() {
        return resourceImport;
    }

    public void setResourceImport(ResourceImportModel resourceImport) {
        this.resourceImport = resourceImport;
    }
}
