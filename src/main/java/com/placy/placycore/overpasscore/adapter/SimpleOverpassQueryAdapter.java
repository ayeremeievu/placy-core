package com.placy.placycore.overpasscore.adapter;

import com.placy.placycore.core.utils.StringParametersUtils;
import com.placy.placycore.overpasscore.query.SimpleOverpassQuery;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author ayeremeiev@netconomy.net
 */
@Component
public class SimpleOverpassQueryAdapter {

    public String resolveOverpassQuery(SimpleOverpassQuery simpleOverpassQuery) {
        String query = simpleOverpassQuery.getQuery();
        Map<String, String> parameters = simpleOverpassQuery.getParameters();

        return StringParametersUtils.resolveParameters(query, parameters);
    }
}
