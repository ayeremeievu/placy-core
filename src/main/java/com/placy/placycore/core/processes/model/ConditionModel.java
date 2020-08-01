package com.placy.placycore.core.processes.model;

import com.placy.placycore.core.model.UuidDomainModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Entity
@Table(name = "conditions",
       indexes = {
           @Index(columnList = "c_code", name = "c_code_idx"),
       },
       uniqueConstraints = {
           @UniqueConstraint(columnNames = "c_code", name = "c_code_unq_constraint")
       }
)
public class ConditionModel extends UuidDomainModel {
    @Column(name = "c_code", nullable = false)
    private String code;

    @Column(name = "c_runnerBean", nullable = false)
    private String runnerBean;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRunnerBean() {
        return runnerBean;
    }

    public void setRunnerBean(String runnerBean) {
        this.runnerBean = runnerBean;
    }
}
