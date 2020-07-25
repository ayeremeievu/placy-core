package com.placy.placycore.overpasscore.query;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ayeremeiev@netconomy.net
 */
public class SimpleOverpassQuery {
    private String query;
    private Map<String, String> parameters = new HashMap<>();

    public SimpleOverpassQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public void putParameter(String key, String value) {
        this.parameters.put(key, value);
    }
}
