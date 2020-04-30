package com.placy.placycore.core.processes.model;

import com.placy.placycore.core.model.DomainModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Entity
@Table(name = "processStepInstanceResults",
   indexes = {
        @Index(columnList = "psir_code", name = "psir_code_idx")
    },
   uniqueConstraints = {
       @UniqueConstraint(columnNames = "psir_code")
   })
public class ProcessStepInstanceResultModel extends DomainModel {
    @Column(name = "psir_processResult", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProcessResultEnum processResult;

    public ProcessResultEnum getProcessResult() {
        return processResult;
    }

    public void setProcessResult(ProcessResultEnum processResult) {
        this.processResult = processResult;
    }
}
