package com.placy.placycore.core.processes.data;

import com.placy.placycore.corewebservices.processes.dto.ParamValueDto;

import java.util.List;

/**
 * @author ayeremeiev@netconomy.net
 */
public class RunProcessData {
    private String processCode;
    private List<ParamValueData> paramValues;

    public RunProcessData() {
    }

    public String getProcessCode() {
        return processCode;
    }

    public void setProcessCode(String processCode) {
        this.processCode = processCode;
    }

    public List<ParamValueData> getParamValues() {
        return paramValues;
    }

    public void setParamValues(List<ParamValueData> paramValues) {
        this.paramValues = paramValues;
    }

    public static RunProcessData of() {
        return new RunProcessData();
    }
}
