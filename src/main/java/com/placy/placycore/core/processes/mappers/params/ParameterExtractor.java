package com.placy.placycore.core.processes.mappers.params;

import com.placy.placycore.core.processes.exceptions.WrongExecutableParamException;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author ayeremeiev@netconomy.net
 */
@Component
public class ParameterExtractor {
    public <T> T extractParameter(Map<String, Object> params, String paramName, Class<T> clazz) {
        Object object = params.get(paramName);

        if(object == null && !(object.getClass().isInstance(clazz))) {
            throw new WrongExecutableParamException(
                String.format("Param with name '{}' either absent or of wrong type", paramName)
            );
        }

        return clazz.cast(object);
    }
}
