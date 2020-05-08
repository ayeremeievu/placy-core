package com.placy.placycore.core.processes.data;

import java.util.List;

/**
 * @author a.yeremeiev@netconomy.net
 */
public class ProcessStepDefinition {
    private String code;
    private String taskCode;
    private List<ParamValueDefinition> paramsValues;
    private List<DelegatingParamDefinition> delegatingParams;

    public ProcessStepDefinition() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public List<ParamValueDefinition> getParamsValues() {
        return paramsValues;
    }

    public void setParamsValues(List<ParamValueDefinition> paramsValues) {
        this.paramsValues = paramsValues;
    }

    public List<DelegatingParamDefinition> getDelegatingParams() {
        return delegatingParams;
    }

    public void setDelegatingParams(List<DelegatingParamDefinition> delegatingParams) {
        this.delegatingParams = delegatingParams;
    }
}
