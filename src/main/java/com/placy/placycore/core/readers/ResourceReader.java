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

    public <T> T readResourceSystemClassLoader(String path, Class<T> clazz) {
        return readResource(ClassLoader.getSystemClassLoader(), path, clazz);
    }

    public <T> T readResource(ClassLoader classLoader, String path, Class<T> clazz) {
        T result = null;

        InputStream resourceAsStream = classLoader.getResourceAsStream(path);

        try {
            result = jsonReader.readJson(resourceAsStream, clazz);
        } catch (IOException e) {
            throw new ResourceReaderException(e);
        }

        return result;
    }
}
