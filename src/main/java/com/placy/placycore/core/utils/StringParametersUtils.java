package com.placy.placycore.core.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * @author ayeremeiev@netconomy.net
 */
public class StringParametersUtils {
    private final static String PARAM_PREFIX = "#{";
    private final static String PARAM_SUFFIX = "}";

    // TODO : all escaping and so on
    public static String resolveParameters(String str, Map<String, String> params) {
        String result = str;

        for (Map.Entry<String, String> paramEntry : params.entrySet()) {
            result = StringUtils.replace(str, formatParamKey(paramEntry.getKey()), paramEntry.getValue());
        }

        return result;
    }

    public static String formatParamKey(String key) {
        return PARAM_PREFIX + key + PARAM_SUFFIX;
    }
}
