package com.placy.placycore.core.processes.model;

import com.placy.placycore.core.model.UuidDomainModel;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Entity
@Table(name = "taskInstances", indexes = {
        @Index(columnList = "ti_code", name = "ti_code_idx"),
    },
    uniqueConstraints = {
       @UniqueConstraint(columnNames = "ti_code", name = "ti_code_unq_constraint")
    }
)
public class TaskInstanceModel extends UuidDomainModel implements ExecutableModel {

    @Column(name = "ti_code", nullable = false)
    private String code;

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

    @PrePersist
    public void prePersist() {
        if (this.code == null) {
            this.code = String.format("%s-%s", task.getCode(), (new Date()).getTime());
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TaskInstanceModel that = (TaskInstanceModel) o;
        return Objects.equals(code, that.code) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(finishDate, that.finishDate) &&
            status == that.status &&
            Objects.equals(task, that.task) &&
            Objects.equals(paramValues, that.paramValues);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, startDate, finishDate, status, task, paramValues);
    }
}
