package com.placy.placycore.core.processes.model;

import com.placy.placycore.core.model.DomainModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Entity
@Table(name = "parameters",
       indexes = {
           @Index(columnList = "p_code", name = "p_code_idx"),
       },
       uniqueConstraints = {
           @UniqueConstraint(columnNames = "p_code")
       }
)
public class ParameterModel extends DomainModel {
    @Column(name = "p_code", nullable = false)
    private String code;

    @Column(name = "p_defaultValue", nullable = true)
    private String defaultValue;

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
}
