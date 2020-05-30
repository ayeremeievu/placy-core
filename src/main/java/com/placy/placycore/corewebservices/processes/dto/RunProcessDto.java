package com.placy.placycore.corewebservices.processes.dto;

import java.util.List;

/**
 * @author ayeremeiev@netconomy.net
 */
public class RunProcessDto {

    private String processCode;
    private List<ParamValueDto> paramValues;

    public RunProcessDto() {
    }

    public String getProcessCode() {
        return processCode;
    }

    public void setProcessCode(String processCode) {
        this.processCode = processCode;
    }

    public List<ParamValueDto> getParamValues() {
        return paramValues;
    }

    public void setParamValues(List<ParamValueDto> paramValues) {
        this.paramValues = paramValues;
    }
}
