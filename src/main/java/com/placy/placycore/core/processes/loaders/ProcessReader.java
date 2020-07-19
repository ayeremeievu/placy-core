package com.placy.placycore.core.processes.loaders;

import com.placy.placycore.core.processes.data.ProcessDefinition;
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
public class ProcessReader {
    Logger LOG = LoggerFactory.getLogger(ProcessReader.class);

    @Autowired
    private PropertiesValidator propertiesValidator;

    @Autowired
    private ResourceReader resourceReader;

    public ProcessDefinition readProcess(String resourceData) {
        ProcessDefinition obj = resourceReader.readResource(resourceData, ProcessDefinition.class);

        propertiesValidator.validate(obj);

        return obj;
    }
}
