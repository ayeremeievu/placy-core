package com.placy.placycore.core.processes.data;

import java.util.List;

/**
 * @author a.yeremeiev@netconomy.net
 */
public class ProcessDefinition {
    private String code;
    private List<ParamDefinition> params;

    private List<ProcessStepDefinition> steps;

    public ProcessDefinition() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<ParamDefinition> getParams() {
        return params;
    }

    public void setParams(List<ParamDefinition> params) {
        this.params = params;
    }

    public List<ProcessStepDefinition> getSteps() {
        return steps;
    }

    public void setSteps(List<ProcessStepDefinition> steps) {
        this.steps = steps;
    }
}
