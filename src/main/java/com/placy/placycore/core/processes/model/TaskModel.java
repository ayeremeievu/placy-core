package com.placy.placycore.core.processes.model;

import com.placy.placycore.core.model.DomainModel;

import java.util.Collection;

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
@Table(name = "tasks", indexes = {
        @Index(columnList = "pk", name = "t_pk_idx"),
        @Index(columnList = "t_code", name = "t_code_idx"),
    },
    uniqueConstraints = {
       @UniqueConstraint(columnNames = "pk"),
       @UniqueConstraint(columnNames = "t_code")
    }
)
public class TaskModel extends DomainModel {
    @Column(name = "t_code", nullable = false)
    private String code;

    @Column(name = "t_name", nullable = true)
    private String name;

    @Column(name = "t_runnerBean", nullable = false)
    private String runnerBean;

    @OneToMany(targetEntity = TaskInstanceModel.class, mappedBy = "task")
    private Collection<TaskInstanceModel> tasksInstances;

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

    public String getRunnerBean() {
        return runnerBean;
    }

    public void setRunnerBean(String runnerBean) {
        this.runnerBean = runnerBean;
    }

    public Collection<TaskInstanceModel> getTasksInstances() {
        return tasksInstances;
    }

    public void setTasksInstances(Collection<TaskInstanceModel> tasksInstances) {
        this.tasksInstances = tasksInstances;
    }
}
