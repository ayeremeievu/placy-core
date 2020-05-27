package com.placy.placycore.core.processes.data;

import com.placy.placycore.corewebservices.processes.dto.ParamValueDto;

import java.util.List;

/**
 * @author ayeremeiev@netconomy.net
 */
public class RunTaskData {
    private String code;
    private List<ParamValueData> paramValues;

    public RunTaskData() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<ParamValueData> getParamValues() {
        return paramValues;
    }

    public void setParamValues(List<ParamValueData> paramValues) {
        this.paramValues = paramValues;
    }
}
