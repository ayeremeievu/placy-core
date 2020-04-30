package com.placy.placycore.core.processes.model;

import com.placy.placycore.core.model.DomainModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Entity
@Table(name = "taskResources",
    indexes = {
        @Index(columnList = "pk", name = "pr_pk_idx"),
    },
    uniqueConstraints = {
       @UniqueConstraint(columnNames = "pk")
    }
)
public class TaskResourceModel extends DomainModel {
    @OneToOne
    @JoinColumn(name = "tr_task_pk", unique = true, nullable = false)
    private TaskModel task;

    @Column(name = "tr_resource", nullable = false)
    private String resource;

    public TaskModel getTask() {
        return task;
    }

    public void setTask(TaskModel task) {
        this.task = task;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}
