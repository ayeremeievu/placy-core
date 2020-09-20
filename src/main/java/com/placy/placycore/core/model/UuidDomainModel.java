package com.placy.placycore.core.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class UuidDomainModel extends AbstractDomainModel<String> implements Identifiable<String> {
    @Id
    @Column(name = "pk")
    private String pk;

    @PrePersist
    public void init() {
        if(pk == null) {
            pk = UUID.randomUUID().toString();
        }
    }

    public UuidDomainModel() {
    }

    @Override
    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    @Override
    public String getId() {
        return pk;
    }
}
