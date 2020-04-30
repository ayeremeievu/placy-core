package com.placy.placycore.core.processes.model;

import com.placy.placycore.core.model.DomainModel;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Entity
@Table(name = "processToParamRels", indexes = {
        @Index(columnList = "pk", name = "t_pk_idx")
    },
    uniqueConstraints = {
       @UniqueConstraint(columnNames = {"ttp_process_pk", "ttp_parameter_pk"}),
       @UniqueConstraint(columnNames = "ttp_parameter_pk")
    }
)
public class TaskToParamRelModel extends DomainModel {
    @ManyToOne
    @JoinColumn(name = "ttp_process_pk", nullable = false)
    private ProcessModel process;

    @ManyToOne
    @JoinColumn(name = "ttp_parameter_pk", nullable = false)
    private ParameterModel parameter;
}
