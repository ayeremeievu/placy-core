package com.placy.placycore.corewebservices.processes.dto;

import java.util.List;

/**
 * @author ayeremeiev@netconomy.net
 */
public class RunTaskDto {
    private String code;
    private List<ParamValueDto> paramValues;

    public RunTaskDto() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<ParamValueDto> getParamValues() {
        return paramValues;
    }

    public void setParamValues(List<ParamValueDto> paramValues) {
        this.paramValues = paramValues;
    }
}
