package com.placy.placycore.core.processes.data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author a.yeremeiev@netconomy.net
 */
public class DelegatingParamDefinition {
    @NotEmpty
    private String processParamCode;
    @NotEmpty
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
