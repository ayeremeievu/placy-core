package com.placy.placycore.core.processes.data;

/**
 * @author ayeremeiev@netconomy.net
 */
public class ParamValueData {
    private String code;
    private String value;

    public ParamValueData() {
    }

    public ParamValueData(String code, String value) {
        this.code = code;
        this.value = value;
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
