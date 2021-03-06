package com.placy.placycore.corewebservices.processes.dto;

import java.util.List;

/**
 * @author a.yeremeiev@netconomy.net
 */
public class ProcessDto {
    private String code;
    private String name;
    private List<ParamDto> params;

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

    public List<ParamDto> getParams() {
        return params;
    }

    public void setParams(List<ParamDto> params) {
        this.params = params;
    }
}
