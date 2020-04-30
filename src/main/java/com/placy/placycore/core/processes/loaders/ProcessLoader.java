package com.placy.placycore.core.processes.loaders;

import com.placy.placycore.core.processes.data.ProcessDefinition;
import com.placy.placycore.core.processes.model.ProcessModel;
import com.placy.placycore.core.processes.populators.ProcessDefinitionToModelPopulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Component
public class ProcessLoader {
    @Autowired
    private ProcessReader processReader;

    @Autowired
    private ProcessDefinitionToModelPopulator processDefinitionToModelPopulator;

    public ProcessModel loadProcess(ClassLoader classLoader, String filepath) {
        ProcessDefinition processDefinition = processReader.readProcess(classLoader, filepath);

        ProcessModel processModel = new ProcessModel();

        processDefinitionToModelPopulator.populate(processDefinition, processModel);

        return processModel;
    }
}
