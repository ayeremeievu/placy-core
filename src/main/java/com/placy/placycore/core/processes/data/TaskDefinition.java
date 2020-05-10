package com.placy.placycore.core.processes.data;

import java.util.List;

import javax.validation.constraints.NotEmpty;

/**
 * @author a.yeremeiev@netconomy.net
 */
public class TaskDefinition {
    @NotEmpty
    private String code;
    @NotEmpty
    private String runnerBean;
    private String name;
    private List<ParamDefinition> params;

    public TaskDefinition() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRunnerBean() {
        return runnerBean;
    }

    public void setRunnerBean(String runnerBean) {
        this.runnerBean = runnerBean;
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
}
