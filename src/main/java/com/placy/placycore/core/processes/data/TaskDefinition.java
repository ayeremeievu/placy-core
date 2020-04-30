package com.placy.placycore.core.processes.data;

import java.util.List;

/**
 * @author a.yeremeiev@netconomy.net
 */
public class TaskDefinition {
    private String code;
    private String runnerBean;
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

    public List<ParamDefinition> getParams() {
        return params;
    }

    public void setParams(List<ParamDefinition> params) {
        this.params = params;
    }
}
