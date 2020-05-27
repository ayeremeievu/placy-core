package com.placy.placycore.core.processes.model;

import com.placy.placycore.core.model.DomainModel;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "taskInstances")
public class TaskInstanceModel extends DomainModel implements ExecutableModel {
    @Column(name = "ti_startDate", nullable = true)
    private Date startDate;

    @Column(name = "ti_finishDate", nullable = true)
    private Date finishDate;

    @Column(name = "ti_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskInstanceStatusEnum status;

    @ManyToOne
    @JoinColumn(name = "ti_task_pk", nullable = false)
    private TaskModel task;

    @OneToMany(mappedBy = "taskInstance", cascade = CascadeType.ALL)
    private List<TaskParameterValueModel> paramValues;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public TaskInstanceStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TaskInstanceStatusEnum status) {
        this.status = status;
    }

    public TaskModel getTask() {
        return task;
    }

    public void setTask(TaskModel task) {
        this.task = task;
    }

    public List<TaskParameterValueModel> getParamValues() {
        return paramValues;
    }

    public void setParamValues(List<TaskParameterValueModel> paramValues) {
        this.paramValues = paramValues;
    }
}
