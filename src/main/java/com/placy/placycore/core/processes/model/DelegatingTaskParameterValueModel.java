package com.placy.placycore.core.processes.model;

import com.placy.placycore.core.model.UuidDomainModel;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Entity
@Table(name = "delegatingTaskParameterValues", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"dtpv_processStep_pk", "dtpv_taskParameter_pk"}, name = "dtpv_processStep_parameter_unq_constraint")
})
public class DelegatingTaskParameterValueModel extends UuidDomainModel {
    @ManyToOne(optional = false)
    @JoinColumn(name = "dtpv_processStep_pk")
    private ProcessStepModel processStep;

    @ManyToOne(optional = false)
    @JoinColumn(name = "dtpv_taskParameter_pk")
    private TaskParameterModel taskParameter;

    @ManyToOne(optional = false)
    @JoinColumn(name = "dtpv_processParameter_pk")
    private ProcessParameterModel processParameterModel;

    public DelegatingTaskParameterValueModel() {
    }

    public ProcessStepModel getProcessStep() {
        return processStep;
    }

    public void setProcessStep(ProcessStepModel processStep) {
        this.processStep = processStep;
    }

    public TaskParameterModel getTaskParameter() {
        return taskParameter;
    }

    public void setTaskParameter(TaskParameterModel taskParameter) {
        this.taskParameter = taskParameter;
    }

    public ProcessParameterModel getProcessParameterModel() {
        return processParameterModel;
    }

    public void setProcessParameterModel(ProcessParameterModel processParameterModel) {
        this.processParameterModel = processParameterModel;
    }
}
