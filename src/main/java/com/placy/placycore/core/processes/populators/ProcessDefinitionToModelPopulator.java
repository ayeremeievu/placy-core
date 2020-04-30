package com.placy.placycore.core.processes.populators;

import com.placy.placycore.core.populators.Populator;
import com.placy.placycore.core.processes.data.ProcessDefinition;
import com.placy.placycore.core.processes.model.ProcessModel;
import org.springframework.stereotype.Component;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Component
public class ProcessDefinitionToModelPopulator implements Populator<ProcessDefinition, ProcessModel> {

    @Override
    public void populate(ProcessDefinition processDefinition, ProcessModel processModel) {
        processDefinition.setCode(processModel.getCode());
    }
}
