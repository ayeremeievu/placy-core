package com.placy.placycore.core.readers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

import static org.apache.commons.lang3.exception.ExceptionUtils.rethrow;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Component
public class JsonReader {
    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();

        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public <T> T readJsonExceptionSuppressed(InputStream inputStream, Class<T> clazz) {
        T result = null;

        try {
            result = readJson(inputStream, clazz);
        } catch (IOException e) {
            rethrow(e);
        }

        return result;
    }

    public <T> T readJson(InputStream inputStream, Class<T> clazz) throws IOException {
        return mapper.readValue(inputStream, clazz);
    }
}
