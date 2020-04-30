package com.placy.placycore.core.processes.data;

/**
 * @author a.yeremeiev@netconomy.net
 */
public class DelegatingParamDefinition {
    private String processParamCode;
    private String taskParamCode;

    public DelegatingParamDefinition() {
    }

    public String getProcessParamCode() {
        return processParamCode;
    }

    public void setProcessParamCode(String processParamCode) {
        this.processParamCode = processParamCode;
    }

    public String getTaskParamCode() {
        return taskParamCode;
    }

    public void setTaskParamCode(String taskParamCode) {
        this.taskParamCode = taskParamCode;
    }
}
