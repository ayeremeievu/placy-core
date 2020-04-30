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
@Table(name = "processResources", indexes = {
        @Index(columnList = "pk", name = "pr_pk_idx"),
    },
    uniqueConstraints = {
       @UniqueConstraint(columnNames = "pk")
    }
)
public class ProcessResourceModel extends DomainModel {
    @OneToOne
    @JoinColumn(name = "pr_process_pk", unique = true, nullable = false)
    private ProcessModel process;

    @Column(name = "pr_resource", nullable = false)
    private String resource;

    public ProcessModel getProcess() {
        return process;
    }

    public void setProcess(ProcessModel process) {
        this.process = process;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}
