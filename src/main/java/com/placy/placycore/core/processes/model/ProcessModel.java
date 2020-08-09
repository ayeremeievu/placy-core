package com.placy.placycore.core.processes.model;

import com.placy.placycore.core.model.UuidDomainModel;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Entity
@Table(name = "processes", indexes = {
        @Index(columnList = "p_code", name = "p_code_idx"),
    },
    uniqueConstraints = {
       @UniqueConstraint(columnNames = "p_code", name = "p_code_unq_constraint")
    }
)
public class ProcessModel extends UuidDomainModel {
    @Column(name = "p_code", nullable = false)
    private String code;

    @Column(name = "p_name", nullable = true)
    private String name;

    @OneToOne(mappedBy = "process")
    private ProcessResourceModel processResource;

    @OneToMany(mappedBy = "process", cascade = CascadeType.ALL)
    @OrderBy("order")
    private List<ProcessStepModel> processSteps;

    @OneToMany(mappedBy = "process", cascade = CascadeType.ALL)
    private List<ProcessInstanceModel> processInstances;

    @OneToMany(mappedBy = "process", cascade = CascadeType.ALL)
    private List<ProcessParameterModel> params;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProcessResourceModel getProcessResource() {
        return processResource;
    }

    public void setProcessResource(ProcessResourceModel processResourceModel) {
        this.processResource = processResourceModel;
    }

    public List<ProcessStepModel> getProcessSteps() {
        return processSteps;
    }

    public void setProcessSteps(List<ProcessStepModel> processSteps) {
        this.processSteps = processSteps;
    }

    public List<ProcessInstanceModel> getProcessInstances() {
        return processInstances;
    }

    public void setProcessInstances(List<ProcessInstanceModel> processInstances) {
        this.processInstances = processInstances;
    }

    public List<ProcessParameterModel> getParams() {
        return params;
    }

    public void setParams(List<ProcessParameterModel> parametersRelations) {
        this.params = parametersRelations;
    }
}
