package com.placy.placycore.core.processes.data;

import com.placy.placycore.core.processes.model.ProcessStepModel;

/**
 * @author a.yeremeiev@netconomy.net
 */
public class ParamValueDefinitionInfo {
    private final ParamValueDefinition paramValueDefinition;
    private final ProcessStepDefinitionInfo processStepDefinitionInfo;
    private final ProcessStepModel processStepModel;

    public ParamValueDefinitionInfo(ParamValueDefinition paramValueDefinition,
                                    ProcessStepDefinitionInfo processStepDefinitionInfo,
                                    ProcessStepModel processStepModel) {
        this.paramValueDefinition = paramValueDefinition;
        this.processStepDefinitionInfo = processStepDefinitionInfo;
        this.processStepModel = processStepModel;
    }

    public ParamValueDefinition getParamValueDefinition() {
        return paramValueDefinition;
    }

    public ProcessStepDefinitionInfo getProcessStepDefinitionInfo() {
        return processStepDefinitionInfo;
    }

    public ProcessStepModel getProcessStepModel() {
        return processStepModel;
    }
}
