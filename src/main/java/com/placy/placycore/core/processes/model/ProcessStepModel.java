package com.placy.placycore.core.processes.model;

import com.placy.placycore.core.model.DomainModel;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Entity
@Table(name = "processSteps",  indexes = {
    @Index(columnList = "ps_code", name = "ps_code_idx")
    },
        uniqueConstraints = {
            @UniqueConstraint(columnNames = "ps_code", name = "ps_code_unq_constraint"),
            @UniqueConstraint(columnNames = {"ps_task_pk","ps_order"}, name = "ps_task_order_unq_constraint")
    })
public class ProcessStepModel extends DomainModel {
    @Column(name = "ps_code", nullable = false)
    private String code;

    @Column(name = "ps_order", nullable = false)
    private int order;

    @OneToOne
    @JoinColumn(name = "ps_task_pk", nullable = false)
    private TaskModel taskModel;

    @OneToOne
    @JoinColumn(name = "ps_condition_pk", nullable = false)
    private ConditionModel condition;

    @ManyToOne
    @JoinColumn(name = "ps_process_pk", nullable = false)
    private ProcessModel process;

    @OneToMany(mappedBy = "processStep")
    private List<PredefinedTaskParameterValueModel> predefinedTaskParameterValues;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public TaskModel getTaskModel() {
        return taskModel;
    }

    public void setTaskModel(TaskModel taskModel) {
        this.taskModel = taskModel;
    }

    public ConditionModel getCondition() {
        return condition;
    }

    public void setCondition(ConditionModel condition) {
        this.condition = condition;
    }

    public ProcessModel getProcess() {
        return process;
    }

    public void setProcess(ProcessModel process) {
        this.process = process;
    }

    public List<PredefinedTaskParameterValueModel> getPredefinedTaskParameterValues() {
        return predefinedTaskParameterValues;
    }

    public void setPredefinedTaskParameterValues(List<PredefinedTaskParameterValueModel> delegatingTaskParameterValues) {
        this.predefinedTaskParameterValues = delegatingTaskParameterValues;
    }
}
