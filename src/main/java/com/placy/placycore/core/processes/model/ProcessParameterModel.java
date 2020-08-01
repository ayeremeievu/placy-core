package com.placy.placycore.core.processes.model;

import com.placy.placycore.core.model.UuidDomainModel;

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
@Table(name = "processParameters",
       indexes = {
           @Index(columnList = "pp_code", name = "pp_code_idx"),
       },
       uniqueConstraints = {
           @UniqueConstraint(columnNames = "pp_code", name = "pp_code_unq_constraint")
       }
)
public class ProcessParameterModel extends UuidDomainModel {
    @Column(name = "pp_code", nullable = false)
    private String code;

    @Column(name = "pp_defaultValue", nullable = true)
    private String defaultValue;

    @Column(name = "pp_isRequired", nullable = false)
    private boolean isRequired = false;

    @OneToMany(cascade = {CascadeType.REMOVE}, mappedBy = "parameter")
    private List<ProcessParameterValueModel> parameterValues;

    @ManyToOne
    @JoinColumn(name = "pp_process")
    private ProcessModel process;

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

    public boolean isRequired() {
        return isRequired;
    }

    public void setRequired(boolean required) {
        isRequired = required;
    }

    public List<ProcessParameterValueModel> getParameterValues() {
        return parameterValues;
    }

    public void setParameterValues(List<ProcessParameterValueModel> parameterValues) {
        this.parameterValues = parameterValues;
    }

    public ProcessModel getProcess() {
        return process;
    }

    public void setProcess(ProcessModel process) {
        this.process = process;
    }
}
