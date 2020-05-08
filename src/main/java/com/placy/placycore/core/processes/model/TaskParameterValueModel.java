package com.placy.placycore.core.processes.model;

import com.placy.placycore.core.model.DomainModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Entity
@Table(name = "taskParameterValues")
public class TaskParameterValueModel extends DomainModel {
    @ManyToOne
    @JoinColumn(name = "tpv_parameter_pk", nullable = false, updatable = false)
    private TaskParameterModel parameter;

    @Column(name = "tpv_value", nullable = false)
    private String value;

    @ManyToOne
    @JoinColumn(name = "tpv_taskInstance_pk", nullable = false, updatable = false)
    private TaskInstanceModel taskInstance;

    public TaskParameterModel getParameter() {
        return parameter;
    }

    public void setParameter(TaskParameterModel parameter) {
        this.parameter = parameter;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public TaskInstanceModel getTaskInstance() {
        return taskInstance;
    }

    public void setTaskInstance(TaskInstanceModel taskInstance) {
        this.taskInstance = taskInstance;
    }
}
