package com.placy.placycore.core.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author ayeremeiev@netconomy.net
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class IncrementalDomainModel extends AbstractDomainModel<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk", nullable = false)
    private Integer pk;

    public IncrementalDomainModel() {
    }

    @Override
    public Integer getPk() {
        return pk;
    }

    public void setPk(Integer id) {
        this.pk = id;
    }
}
