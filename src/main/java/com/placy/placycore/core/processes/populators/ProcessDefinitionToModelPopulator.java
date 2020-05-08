package com.placy.placycore.core.processes.populators;

import com.placy.placycore.core.populators.Populator;
import com.placy.placycore.core.processes.data.ParamDefinition;
import com.placy.placycore.core.processes.data.ProcessDefinition;
import com.placy.placycore.core.processes.model.ProcessModel;
import com.placy.placycore.core.processes.model.ProcessParameterModel;
import com.placy.placycore.core.processes.model.TaskParameterModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Component
public class ProcessDefinitionToModelPopulator implements Populator<ProcessDefinition, ProcessModel> {
    @Autowired
    private ParamDefinitionToProcessParamModelPopulator paramDefinitionToTaskParamModelPopulator;

    @Override
    public void populate(ProcessDefinition processDefinition, ProcessModel processModel) {
        processDefinition.setCode(processModel.getCode());
    }

    private List<ProcessParameterModel> getProcessParameters(ProcessDefinition processDefinition, ProcessModel processModel) {
        List<ProcessParameterModel> processParameters = new ArrayList<>();
        List<ParamDefinition> params = processDefinition.getParams();
        params.stream().map(paramDefinition -> {
            ProcessParameterModel parameterModel = new ProcessParameterModel();

            paramDefinitionToTaskParamModelPopulator.populate(paramDefinition, parameterModel);

            parameterModel.setProcess(processModel);

            return parameterModel;
        }).forEach(processParameters::add);

        return processParameters;
    }
}
