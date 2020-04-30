package com.placy.placycore.core.processes.data;

/**
 * @author a.yeremeiev@netconomy.net
 */
public class ParamDefinition {
    private String code;
    private String defaultValue;

    public ParamDefinition() {
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
