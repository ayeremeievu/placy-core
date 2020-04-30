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
       @UniqueConstraint(columnNames = {"ptp_process_pk", "ptp_parameter_pk"}),
       @UniqueConstraint(columnNames = "ptp_parameter_pk")
    }
)
public class ProcessToParamRelModel extends DomainModel {
    @ManyToOne
    @JoinColumn(name = "ptp_process_pk", nullable = false)
    private ProcessModel process;

    @ManyToOne
    @JoinColumn(name = "ptp_parameter_pk", nullable = false)
    private ParameterModel parameter;
}
