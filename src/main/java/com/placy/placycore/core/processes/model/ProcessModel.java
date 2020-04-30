package com.placy.placycore.core.processes.model;

import com.placy.placycore.core.model.DomainModel;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Entity
@Table(name = "processes", indexes = {
        @Index(columnList = "pk", name = "p_pk_idx"),
        @Index(columnList = "p_code", name = "p_code_idx"),
    },
    uniqueConstraints = {
       @UniqueConstraint(columnNames = "pk"),
       @UniqueConstraint(columnNames = "p_code")
    }
)
public class ProcessModel extends DomainModel {
    @Column(name = "p_code", nullable = false)
    private String code;

    @OneToMany(mappedBy = "process")
    private List<ProcessStepModel> processSteps;

    @OneToMany(mappedBy = "process")
    private List<ProcessInstanceModel> processInstances;

    @OneToMany(mappedBy = "process")
    private List<ProcessToParamRelModel> parametersRelations;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public List<ProcessToParamRelModel> getParametersRelations() {
        return parametersRelations;
    }

    public void setParametersRelations(List<ProcessToParamRelModel> parametersRelations) {
        this.parametersRelations = parametersRelations;
    }
}
