package com.placy.placycore.core.processes.populators;

import com.placy.placycore.core.populators.Populator;
import com.placy.placycore.core.processes.data.ParamDefinition;
import com.placy.placycore.core.processes.data.ProcessDefinition;
import com.placy.placycore.core.processes.data.ProcessStepDefinition;
import com.placy.placycore.core.processes.data.ProcessStepDefinitionInfo;
import com.placy.placycore.core.processes.model.ProcessModel;
import com.placy.placycore.core.processes.model.ProcessParameterModel;
import com.placy.placycore.core.processes.model.ProcessStepModel;
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
    private ProcessStepDefinitionToProcessStepModelPopulator processStepDefinitionToProcessStepModelPopulator;

    @Autowired
    private ParamDefinitionToProcessParamModelPopulator paramDefinitionToTaskParamModelPopulator;

    @Override
    public void populate(ProcessDefinition processDefinition, ProcessModel processModel) {
        processModel.setCode(processDefinition.getCode());
        processModel.setName(processDefinition.getName());
        processModel.setParams(getProcessParameters(processDefinition, processModel));
        processModel.setProcessSteps(getProcessSteps(processDefinition, processModel));
    }

    private List<ProcessStepModel> getProcessSteps(ProcessDefinition processDefinition, ProcessModel processModel) {
        List<ProcessStepModel> processSteps = new ArrayList<>();
        List<ProcessStepDefinition> stepsDefinitions = processDefinition.getSteps();

        if(stepsDefinitions == null) {
            stepsDefinitions = new ArrayList<>();
        }

        for(int i = 0; i < stepsDefinitions.size(); i++) {
            ProcessStepDefinition processStepDefinition = stepsDefinitions.get(i);

            processSteps.add(
                convertIntoProcessStepModel(
                    new ProcessStepDefinitionInfo(processStepDefinition, i, processModel)
                )
            );
        }

        return processSteps;
    }

    private ProcessStepModel convertIntoProcessStepModel(ProcessStepDefinitionInfo processStepDefinitionInfo) {
        ProcessStepModel processStepModel = new ProcessStepModel();

        processStepDefinitionToProcessStepModelPopulator.populate(processStepDefinitionInfo, processStepModel);

        return processStepModel;
    }

    private List<ProcessParameterModel> getProcessParameters(ProcessDefinition processDefinition, ProcessModel processModel) {
        List<ProcessParameterModel> processParameters = new ArrayList<>();
        List<ParamDefinition> params = processDefinition.getParams();

        if(params == null) {
            params = new ArrayList<>();
        }

        params.stream().map(paramDefinition -> {
            ProcessParameterModel parameterModel = new ProcessParameterModel();

            paramDefinitionToTaskParamModelPopulator.populate(paramDefinition, parameterModel);

            parameterModel.setProcess(processModel);

            return parameterModel;
        }).forEach(processParameters::add);

        return processParameters;
    }
}
