package com.placy.placycore.core.processes.data;

import javax.validation.constraints.NotEmpty;

/**
 * @author a.yeremeiev@netconomy.net
 */
public class ParamValueDefinition {
    @NotEmpty
    private String code;
    @NotEmpty
    private String value;

    public ParamValueDefinition() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
