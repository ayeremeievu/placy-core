package com.placy.placycore.core.processes.data;

import com.placy.placycore.core.processes.model.ProcessModel;
import com.placy.placycore.core.processes.model.ProcessStepModel;

/**
 * @author a.yeremeiev@netconomy.net
 */
public class DelegatingParamDefinitionInfo {

    private final DelegatingParamDefinition delegatingParamDefinition;
    private final ProcessStepDefinitionInfo processStepDefinitionInfo;
    private final ProcessStepModel processStepModel;

    public DelegatingParamDefinitionInfo(DelegatingParamDefinition delegatingParamDefinition,
                                         ProcessStepDefinitionInfo processStepDefinitionInfo,
                                         ProcessStepModel processStepModel) {
        this.delegatingParamDefinition = delegatingParamDefinition;
        this.processStepDefinitionInfo = processStepDefinitionInfo;
        this.processStepModel = processStepModel;
    }

    public DelegatingParamDefinition getDelegatingParamDefinition() {
        return delegatingParamDefinition;
    }

    public ProcessStepDefinitionInfo getProcessStepDefinitionInfo() {
        return processStepDefinitionInfo;
    }

    public ProcessStepModel getProcessStepModel() {
        return processStepModel;
    }
}
