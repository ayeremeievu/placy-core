package com.placy.placycore.core.processes.model;

import com.placy.placycore.core.model.DomainModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Entity
@Table(name = "predefinedTaskParameterValues", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"ptpv_processStep_pk", "ptpv_taskParameter_pk"}, name = "ptpv_processStep_parameter_unq_constraint")
})
public class PredefinedTaskParameterValueModel extends DomainModel {
    @ManyToOne(optional = false)
    @JoinColumn(name = "ptpv_processStep_pk")
    private ProcessStepModel processStep;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ptpv_taskParameter_pk")
    private TaskParameterModel taskParameter;

    @Column(name = "ptpv_value", nullable = false)
    private String value;

    public ProcessStepModel getProcessStep() {
        return processStep;
    }

    public void setProcessStep(ProcessStepModel processStep) {
        this.processStep = processStep;
    }

    public TaskParameterModel getTaskParameter() {
        return taskParameter;
    }

    public void setTaskParameter(TaskParameterModel parameter) {
        this.taskParameter = parameter;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
