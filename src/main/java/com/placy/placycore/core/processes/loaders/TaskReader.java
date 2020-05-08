package com.placy.placycore.core.processes.loaders;

import com.placy.placycore.core.processes.data.TaskDefinition;
import com.placy.placycore.core.readers.ResourceReader;
import com.placy.placycore.core.validation.PropertiesValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Component
public class TaskReader {
    Logger LOG = LoggerFactory.getLogger(TaskReader.class);

    @Autowired
    private PropertiesValidator propertiesValidator;

    @Autowired
    private ResourceReader resourceReader;

    public TaskDefinition readTask( String path) {
        LOG.info("Reading task : " + path);

        TaskDefinition obj = resourceReader.readResource(path, TaskDefinition.class);

        propertiesValidator.validate(obj);

        return obj;
    }
}
