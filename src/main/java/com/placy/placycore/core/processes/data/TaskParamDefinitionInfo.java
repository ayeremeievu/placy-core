package com.placy.placycore.core.processes.data;

/**
 * @author a.yeremeiev@netconomy.net
 */
public class TaskParamDefinitionInfo {
    private ParamDefinition paramDefinition;

    private TaskDefinition taskDefinition;

    public TaskParamDefinitionInfo(ParamDefinition paramDefinition, TaskDefinition taskDefinition) {
        this.paramDefinition = paramDefinition;
        this.taskDefinition = taskDefinition;
    }

    public ParamDefinition getParamDefinition() {
        return paramDefinition;
    }

    public TaskDefinition getTaskDefinition() {
        return taskDefinition;
    }
}
