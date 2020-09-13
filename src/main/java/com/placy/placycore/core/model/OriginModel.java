package com.placy.placycore.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "origins")
public class OriginModel extends UuidDomainModel {
    @Column(name = "o_code")
    private String code;

    public OriginModel() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
