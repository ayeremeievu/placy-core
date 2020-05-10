package com.placy.placycore.core.processes.loaders;

import com.placy.placycore.core.processes.data.ProcessDefinition;
import com.placy.placycore.core.processes.exceptions.ExecutiveEntityLoadingException;
import com.placy.placycore.core.processes.model.ProcessModel;
import com.placy.placycore.core.processes.populators.ProcessDefinitionToModelPopulator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Component
public class ProcessLoader {
    Logger LOG = LoggerFactory.getLogger(ProcessLoader.class);

    @Autowired
    private ProcessReader processReader;

    @Autowired
    private ProcessDefinitionToModelPopulator processDefinitionToModelPopulator;

    public ProcessModel loadProcess(String filepath) {
        LOG.info("Loading process : " + filepath);
        ProcessModel processModel = new ProcessModel();

        try {
            ProcessDefinition processDefinition = processReader.readProcess(filepath);

            processDefinitionToModelPopulator.populate(processDefinition, processModel);
        } catch (RuntimeException ex) {
            throw new ExecutiveEntityLoadingException("Error occurred during process load process", ex);
        }

        return processModel;
    }
}
