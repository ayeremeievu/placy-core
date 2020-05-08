package com.placy.placycore.core.processes.data;

import com.placy.placycore.core.processes.model.ProcessModel;

/**
 * @author a.yeremeiev@netconomy.net
 */
public class ProcessStepDefinitionInfo {
    private ProcessStepDefinition processStepDefinition;

    private int order;

    private ProcessModel processModel;

    public ProcessStepDefinitionInfo(ProcessStepDefinition processStepDefinition,
                                     int order,
                                     ProcessModel processModel) {
        this.processStepDefinition = processStepDefinition;
        this.order = order;
        this.processModel = processModel;
    }

    public ProcessStepDefinition getProcessStepDefinition() {
        return processStepDefinition;
    }

    public int getOrder() {
        return order;
    }

    public ProcessModel getProcessModel() {
        return processModel;
    }
}
