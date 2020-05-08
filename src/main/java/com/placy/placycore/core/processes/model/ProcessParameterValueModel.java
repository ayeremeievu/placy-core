package com.placy.placycore.core.processes.model;

import com.placy.placycore.core.model.DomainModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Entity
@Table(name = "processParameterValues")
public class ProcessParameterValueModel extends DomainModel {
    @ManyToOne
    @JoinColumn(name = "ppv_parameter_pk", nullable = false)
    private ProcessParameterModel parameter;

    @Column(name = "ppv_value", nullable = false)
    private String value;

    public ProcessParameterModel getParameter() {
        return parameter;
    }

    public void setParameter(ProcessParameterModel parameter) {
        this.parameter = parameter;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
