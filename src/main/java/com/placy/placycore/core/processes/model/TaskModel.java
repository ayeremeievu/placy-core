package com.placy.placycore.core.processes.model;

import com.placy.placycore.core.model.UuidDomainModel;

import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Entity
@Table(name = "tasks", indexes = {
        @Index(columnList = "t_code", name = "t_code_idx"),
    },
    uniqueConstraints = {
       @UniqueConstraint(columnNames = "t_code", name = "t_code_unq_constraint")
    }
)
public class TaskModel extends UuidDomainModel {
    @Column(name = "t_code", nullable = false)
    private String code;

    @Column(name = "t_name", nullable = true)
    private String name;

    @OneToOne(mappedBy = "task")
    private TaskResourceModel taskResource;

    @Column(name = "t_runnerBean", nullable = false)
    private String runnerBean;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<TaskInstanceModel> tasksInstances;

    @OneToMany(mappedBy = "task", cascade = {CascadeType.ALL})
    private List<TaskParameterModel> params;

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

    public TaskResourceModel getTaskResource() {
        return taskResource;
    }

    public void setTaskResource(TaskResourceModel taskResourceModel) {
        this.taskResource = taskResourceModel;
    }

    public String getRunnerBean() {
        return runnerBean;
    }

    public void setRunnerBean(String runnerBean) {
        this.runnerBean = runnerBean;
    }

    public List<TaskInstanceModel> getTasksInstances() {
        return tasksInstances;
    }

    public void setTasksInstances(List<TaskInstanceModel> tasksInstances) {
        this.tasksInstances = tasksInstances;
    }

    public List<TaskParameterModel> getParams() {
        return params;
    }

    public void setParams(List<TaskParameterModel> params) {
        this.params = params;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TaskModel taskModel = (TaskModel) o;
        return Objects.equals(code, taskModel.code) &&
            Objects.equals(name, taskModel.name) &&
            Objects.equals(runnerBean, taskModel.runnerBean) &&
            Objects.equals(params, taskModel.params);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name, runnerBean, params);
    }
}
