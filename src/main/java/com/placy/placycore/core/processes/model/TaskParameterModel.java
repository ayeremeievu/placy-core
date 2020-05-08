package com.placy.placycore.core.processes.model;

import com.placy.placycore.core.model.DomainModel;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Entity
@Table(name = "tasksParameters",
       indexes = {
           @Index(columnList = "tp_code", name = "tp_code_idx"),
       },
       uniqueConstraints = {
           @UniqueConstraint(columnNames = {"tp_code", "tp_task"}, name = "tp_code_task_unq_constraint")
       }
)
public class TaskParameterModel extends DomainModel {
    @Column(name = "tp_code", nullable = false)
    private String code;

    @Column(name = "tp_defaultValue", nullable = true)
    private String defaultValue;

    @OneToMany(cascade = {CascadeType.REMOVE}, mappedBy = "parameter")
    private List<TaskParameterValueModel> parameterValues;

    @ManyToOne
    @JoinColumn(name = "tp_task")
    private TaskModel task;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public List<TaskParameterValueModel> getParameterValues() {
        return parameterValues;
    }

    public void setParameterValues(List<TaskParameterValueModel> parameterValues) {
        this.parameterValues = parameterValues;
    }

    public TaskModel getTask() {
        return task;
    }

    public void setTask(TaskModel taskModel) {
        this.task = taskModel;
    }
}
