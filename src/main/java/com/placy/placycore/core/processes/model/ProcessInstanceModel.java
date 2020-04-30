package com.placy.placycore.core.processes.model;

import com.placy.placycore.core.model.DomainModel;

import java.util.Date;
import java.util.List;

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
@Table(name = "processInstances", indexes = {
        @Index(columnList = "pi_code", name = "pi_code_idx"),
    },
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "pi_code")
    }
)
public class ProcessInstanceModel extends DomainModel {
    @Column(name = "pi_code", nullable = false)
    private String code;

    @Column(name = "pi_startDate", nullable = true)
    private Date startDate;

    @Column(name = "pi_finishDate", nullable = true)
    private Date finishDate;

    @ManyToOne
    @JoinColumn(name = "pi_process_pk", nullable = false)
    private ProcessModel process;

    @OneToMany(mappedBy = "processInstance")
    private List<ProcessStepInstanceModel> processStepsInstances;

    public ProcessModel getProcess() {
        return process;
    }

    public void setProcess(ProcessModel process) {
        this.process = process;
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

    public List<ProcessStepInstanceModel> getProcessStepsInstances() {
        return processStepsInstances;
    }

    public void setProcessStepsInstances(List<ProcessStepInstanceModel> processStepsInstances) {
        this.processStepsInstances = processStepsInstances;
    }
}
