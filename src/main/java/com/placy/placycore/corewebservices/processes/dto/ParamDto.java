package com.placy.placycore.corewebservices.processes.dto;

/**
 * @author a.yeremeiev@netconomy.net
 */
public class ParamDto {
    private String code;
    private String defaultValue;

    public ParamDto() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
