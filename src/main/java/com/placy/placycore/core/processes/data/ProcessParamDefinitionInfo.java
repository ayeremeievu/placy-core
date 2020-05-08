package com.placy.placycore.core.processes.data;

/**
 * @author a.yeremeiev@netconomy.net
 */
public class ProcessParamDefinitionInfo {

    private ParamDefinition paramDefinition;
    private ProcessDefinition processDefinition;

    public ProcessParamDefinitionInfo(ParamDefinition paramDefinition,
                                      ProcessDefinition processDefinition) {
        this.paramDefinition = paramDefinition;
        this.processDefinition = processDefinition;
    }

    public ParamDefinition getParamDefinition() {
        return paramDefinition;
    }

    public ProcessDefinition getProcessDefinition() {
        return processDefinition;
    }
}
