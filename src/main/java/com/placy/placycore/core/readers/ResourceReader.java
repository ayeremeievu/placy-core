package com.placy.placycore.core.readers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Component
public class ResourceReader {
    @Autowired
    private JsonReader jsonReader;

    public <T> T readResource(String resourceData, Class<T> clazz) {
        T result = null;

        try {
            result = jsonReader.readJson(resourceData, clazz);
        } catch (IOException e) {
            throw new ResourceReaderException(e);
        }

        return result;
    }
}
