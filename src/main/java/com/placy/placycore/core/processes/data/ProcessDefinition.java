package com.placy.placycore.core.processes.data;

import java.util.List;

/**
 * @author a.yeremeiev@netconomy.net
 */
public class ProcessDefinition {
    private String code;
    private String name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
