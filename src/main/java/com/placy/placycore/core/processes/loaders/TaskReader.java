package com.placy.placycore.core.processes.loaders;

import com.placy.placycore.core.processes.data.TaskDefinition;
import com.placy.placycore.core.readers.ResourceReader;
import com.placy.placycore.core.validation.PropertiesValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Component
public class TaskReader {
    @Autowired
    private PropertiesValidator propertiesValidator;

    @Autowired
    private ResourceReader resourceReader;

    public TaskDefinition readTask(String path) {
        return readTask(ClassLoader.getSystemClassLoader(), path);
    }

    public TaskDefinition readTask(ClassLoader classLoader, String path) {
        TaskDefinition obj = resourceReader.readResource(classLoader, path, TaskDefinition.class);

        propertiesValidator.validate(obj);

        return obj;
    }
}
