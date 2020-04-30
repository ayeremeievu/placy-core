package com.placy.placycore.core.processes.model;

import com.placy.placycore.core.model.DomainModel;

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
@Table(name = "parameterValues")
public class ParameterValueModel extends DomainModel {
    @ManyToOne
    @JoinColumn(name = "pv_parameter_pk", nullable = false)
    private ParameterModel parameter;

    @Column(name = "pv_value", nullable = false)
    private String value;

    public ParameterModel getParameter() {
        return parameter;
    }

    public void setParameter(ParameterModel parameter) {
        this.parameter = parameter;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
