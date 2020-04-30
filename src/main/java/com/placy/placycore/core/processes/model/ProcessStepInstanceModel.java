package com.placy.placycore.core.processes.model;

import com.placy.placycore.core.model.DomainModel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
           @UniqueConstraint(columnNames = "psi_code")
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
    @JoinColumn(name = "psi_processStep_pk")
    private ProcessStepModel processStep;

    @OneToOne
    @JoinColumn(name = "psi_processStepInstanceResult_pk")
    private ProcessStepInstanceResultModel processStepInstanceResult;

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

    public ProcessStepModel getProcessStep() {
        return processStep;
    }

    public void setProcessStep(ProcessStepModel processStep) {
        this.processStep = processStep;
    }

    public ProcessStepInstanceResultModel getProcessStepInstanceResult() {
        return processStepInstanceResult;
    }

    public void setProcessStepInstanceResult(ProcessStepInstanceResultModel processStepInstanceResult) {
        this.processStepInstanceResult = processStepInstanceResult;
    }
}
