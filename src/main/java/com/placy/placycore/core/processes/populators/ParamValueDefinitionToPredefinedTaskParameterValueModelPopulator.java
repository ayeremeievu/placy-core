package com.placy.placycore.core.processes.populators;

import com.placy.placycore.core.populators.Populator;
import com.placy.placycore.core.processes.data.ParamValueDefinitionInfo;
import com.placy.placycore.core.processes.data.ProcessStepDefinition;
import com.placy.placycore.core.processes.exceptions.MissingFieldLoadingException;
import com.placy.placycore.core.processes.exceptions.TaskParamNotFoundException;
import com.placy.placycore.core.processes.model.PredefinedTaskParameterValueModel;
import com.placy.placycore.core.processes.model.ProcessStepModel;
import com.placy.placycore.core.processes.model.TaskParameterModel;
import com.placy.placycore.core.processes.services.TaskParametersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Component
public class ParamValueDefinitionToPredefinedTaskParameterValueModelPopulator implements Populator<ParamValueDefinitionInfo, PredefinedTaskParameterValueModel> {

    @Autowired
    private TaskParametersService taskParametersService;

    @Override
    public void populate(ParamValueDefinitionInfo paramValueDefinitionInfo, PredefinedTaskParameterValueModel predefinedTaskParameterValueModel) {
        populateProcessStemModule(paramValueDefinitionInfo, predefinedTaskParameterValueModel);
        populateTaskParameterModel(paramValueDefinitionInfo, predefinedTaskParameterValueModel);
        populateParamValue(paramValueDefinitionInfo, predefinedTaskParameterValueModel);
    }

    private void populateProcessStemModule(ParamValueDefinitionInfo paramValueDefinitionInfo,
                                           PredefinedTaskParameterValueModel predefinedTaskParameterValueModel) {
        ProcessStepModel processStepModel = paramValueDefinitionInfo.getProcessStepModel();
        predefinedTaskParameterValueModel.setProcessStep(processStepModel);
    }

    private void populateTaskParameterModel(ParamValueDefinitionInfo paramValueDefinitionInfo,
                                            PredefinedTaskParameterValueModel predefinedTaskParameterValueModel) {
        String paramCode = paramValueDefinitionInfo.getParamValueDefinition().getCode();
        String taskCode = getTaskCode(paramValueDefinitionInfo);

        if(paramCode == null) {
            throw new MissingFieldLoadingException("paramsValues..code");
        }

        if(taskCode == null) {
            throw new MissingFieldLoadingException("steps..taskCode");
        }

        predefinedTaskParameterValueModel.setTaskParameter(getTaskParameterModel(paramCode, taskCode));
    }


    private void populateParamValue(ParamValueDefinitionInfo paramValueDefinitionInfo,
                                    PredefinedTaskParameterValueModel predefinedTaskParameterValueModel) {
        predefinedTaskParameterValueModel.setValue(
            paramValueDefinitionInfo.getParamValueDefinition().getValue()
        );
    }

    private TaskParameterModel getTaskParameterModel(String paramCode, String taskCode) {
        return taskParametersService.getTaskParameterOptional(paramCode, taskCode)
                                    .orElseThrow(() -> new TaskParamNotFoundException(paramCode, taskCode));
    }

    private String getTaskCode(ParamValueDefinitionInfo paramValueDefinitionInfo) {
        ProcessStepDefinition processStepDefinition = paramValueDefinitionInfo.getProcessStepDefinitionInfo().getProcessStepDefinition();
        return processStepDefinition.getTaskCode();
    }
}
