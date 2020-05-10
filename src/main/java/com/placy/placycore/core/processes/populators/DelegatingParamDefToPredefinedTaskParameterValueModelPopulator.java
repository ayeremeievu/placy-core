package com.placy.placycore.core.processes.populators;

import com.placy.placycore.core.populators.Populator;
import com.placy.placycore.core.processes.data.DelegatingParamDefinitionInfo;
import com.placy.placycore.core.processes.data.ProcessStepDefinition;
import com.placy.placycore.core.processes.exceptions.MissingFieldLoadingException;
import com.placy.placycore.core.processes.exceptions.ProcessParamNotFoundInDefinitionException;
import com.placy.placycore.core.processes.exceptions.TaskParamNotFoundException;
import com.placy.placycore.core.processes.model.DelegatingTaskParameterValueModel;
import com.placy.placycore.core.processes.model.ProcessModel;
import com.placy.placycore.core.processes.model.ProcessParameterModel;
import com.placy.placycore.core.processes.model.ProcessStepModel;
import com.placy.placycore.core.processes.model.TaskParameterModel;
import com.placy.placycore.core.processes.services.TaskParametersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Component
public class DelegatingParamDefToPredefinedTaskParameterValueModelPopulator implements Populator<DelegatingParamDefinitionInfo, DelegatingTaskParameterValueModel> {
    @Autowired
    private TaskParametersService taskParametersService;

    @Override
    public void populate(DelegatingParamDefinitionInfo delegatingParamDefinitionInfo,
                         DelegatingTaskParameterValueModel delegatingTaskParameterValueModel) {

        populateProcessStemModule(delegatingParamDefinitionInfo, delegatingTaskParameterValueModel);
        populateTaskParameterModel(delegatingParamDefinitionInfo, delegatingTaskParameterValueModel);
        populateProcessParameterModel(delegatingParamDefinitionInfo, delegatingTaskParameterValueModel);
    }

    private void populateProcessStemModule(DelegatingParamDefinitionInfo delegatingParamValueDefinitionInfo,
                                           DelegatingTaskParameterValueModel predefinedTaskParameterValueModel) {
        ProcessStepModel processStepModel = delegatingParamValueDefinitionInfo.getProcessStepModel();
        predefinedTaskParameterValueModel.setProcessStep(processStepModel);
    }

    private void populateTaskParameterModel(DelegatingParamDefinitionInfo delegatingParamDefinitionInfo,
                                            DelegatingTaskParameterValueModel predefinedTaskParameterValueModel) {
        String paramCode = delegatingParamDefinitionInfo.getDelegatingParamDefinition().getTaskParamCode();
        String taskCode = getTaskCode(delegatingParamDefinitionInfo);

        if(paramCode == null) {
            throw new MissingFieldLoadingException("delegatingParams..taskParamCode");
        }

        if(taskCode == null) {
            throw new MissingFieldLoadingException("steps..taskCode");
        }

        predefinedTaskParameterValueModel.setTaskParameter(getTaskParameterModel(paramCode, taskCode));
    }


    private void populateProcessParameterModel(DelegatingParamDefinitionInfo delegatingParamDefinitionInfo,
                                               DelegatingTaskParameterValueModel predefinedTaskParameterValueModel) {
        List<ProcessParameterModel> processParams = getProcessParams(delegatingParamDefinitionInfo);

        if(processParams == null) {
            processParams = new ArrayList<>();
        }

        String processParamCode = getProcessParamCode(delegatingParamDefinitionInfo);
        String processCode = getProcessCode(delegatingParamDefinitionInfo);

        Optional<ProcessParameterModel> processParameterModelOptional = processParams.stream()
                                                           .filter(processParameterModel ->
                                                                       processParameterModel.getCode().equals(processParamCode)
                                                           ).findAny();

        ProcessParameterModel processParameterModel = processParameterModelOptional.orElseThrow(() ->
                new ProcessParamNotFoundInDefinitionException(
                    processParamCode,
                    processCode)
        );

        predefinedTaskParameterValueModel.setProcessParameterModel(processParameterModel);
    }

    private String getProcessCode(DelegatingParamDefinitionInfo delegatingParamDefinitionInfo) {
        return getProcess(delegatingParamDefinitionInfo).getCode();
    }

    private ProcessModel getProcess(DelegatingParamDefinitionInfo delegatingParamDefinitionInfo) {
        return delegatingParamDefinitionInfo.getProcessStepDefinitionInfo().getProcessModel();
    }

    private String getProcessParamCode(DelegatingParamDefinitionInfo delegatingParamDefinitionInfo) {
        return delegatingParamDefinitionInfo.getDelegatingParamDefinition().getProcessParamCode();
    }

    private TaskParameterModel getTaskParameterModel(String paramCode, String taskCode) {
        return taskParametersService.getTaskParameterOptional(paramCode, taskCode)
                                    .orElseThrow(() -> new TaskParamNotFoundException(paramCode, taskCode));
    }

    private String getTaskCode(DelegatingParamDefinitionInfo delegatingParamDefinitionInfo) {
        ProcessStepDefinition processStepDefinition = delegatingParamDefinitionInfo.getProcessStepDefinitionInfo().getProcessStepDefinition();
        return processStepDefinition.getTaskCode();
    }

    private List<ProcessParameterModel> getProcessParams(DelegatingParamDefinitionInfo delegatingParamDefinitionInfo) {
        return getProcess(delegatingParamDefinitionInfo).getParams();
    }
}
