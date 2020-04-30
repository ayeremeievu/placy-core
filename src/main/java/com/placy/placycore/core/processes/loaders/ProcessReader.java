package com.placy.placycore.core.processes.loaders;

import com.placy.placycore.core.processes.data.ProcessDefinition;
import com.placy.placycore.core.readers.ResourceReader;
import com.placy.placycore.core.validation.PropertiesValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Component
public class ProcessReader {
    @Autowired
    private PropertiesValidator propertiesValidator;

    @Autowired
    private ResourceReader resourceReader;

    public ProcessDefinition readProcess(String path) {
        return readProcess(ClassLoader.getSystemClassLoader(), path);
    }

    public ProcessDefinition readProcess(ClassLoader classLoader, String path) {
        ProcessDefinition obj = resourceReader.readResource(classLoader, path, ProcessDefinition.class);

        propertiesValidator.validate(obj);

        return obj;
    }
}
