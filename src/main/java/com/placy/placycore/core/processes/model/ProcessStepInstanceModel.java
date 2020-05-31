package com.placy.placycore.core.processes.model;

import com.placy.placycore.core.model.DomainModel;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Entity
@Table(name = "processStepInstances",
       indexes = {
           @Index(columnList = "psi_code", name = "psi_code_idx")
       },
       uniqueConstraints = {
           @UniqueConstraint(columnNames = "psi_code", name = "psi_code_unq_constraint")
       })
public class ProcessStepInstanceModel extends DomainModel {
    @Column(name = "psi_code", nullable = false)
    private String code;

    @Column(name = "psi_startDate", nullable = true)
    private Date startDate;

    @Column(name = "psi_finishDate", nullable = true)
    private Date finishDate;

    @ManyToOne
    @JoinColumn(name = "psi_processInstance_pk")
    private ProcessInstanceModel processInstance;

    @OneToOne
    @JoinColumn(name = "psi_taskInstance_pk")
    private TaskInstanceModel taskInstanceModel;

    @OneToOne
    @JoinColumn(name = "psi_processStep_pk")
    private ProcessStepModel processStep;

    @Column(name = "psi_processStepResult", nullable = true)
    @Enumerated(EnumType.STRING)
    private ProcessStepResultEnum processStepResult;

    @PrePersist
    public void prePersist() {
        if(this.code == null) {
            this.code = String.format("%s-%s", processStep.getCode(), (new Date()).getTime());
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

    public ProcessInstanceModel getProcessInstance() {
        return processInstance;
    }

    public void setProcessInstance(ProcessInstanceModel processInstance) {
        this.processInstance = processInstance;
    }

    public TaskInstanceModel getTaskInstanceModel() {
        return taskInstanceModel;
    }

    public void setTaskInstanceModel(TaskInstanceModel taskInstanceModel) {
        this.taskInstanceModel = taskInstanceModel;
    }

    public ProcessStepModel getProcessStep() {
        return processStep;
    }

    public void setProcessStep(ProcessStepModel processStep) {
        this.processStep = processStep;
    }

    public ProcessStepResultEnum getProcessStepResult() {
        return processStepResult;
    }

    public void setProcessStepResult(ProcessStepResultEnum processResult) {
        this.processStepResult = processResult;
    }
}
