package com.placy.placycore.core.processes.model;

import com.placy.placycore.core.model.UuidDomainModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Entity
@Table(name = "taskResources")
public class TaskResourceModel extends UuidDomainModel {
    @OneToOne
    @JoinColumn(name = "tr_task_pk", unique = true)
    private TaskModel task;

    @Column(name = "tr_resource_name", nullable = false)
    private String resourceName;

    @Column(name = "tr_resource_value", nullable = false)
    private String resourceValue;

    @Column(name = "tr_resource_checksum", nullable = false)
    private String resourceChecksum;

    public TaskModel getTask() {
        return task;
    }

    public void setTask(TaskModel task) {
        this.task = task;
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
