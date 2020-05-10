package com.placy.placycore.core.processes.populators;

import com.placy.placycore.core.populators.Populator;
import com.placy.placycore.core.processes.data.DelegatingParamDefinition;
import com.placy.placycore.core.processes.data.DelegatingParamDefinitionInfo;
import com.placy.placycore.core.processes.data.ParamValueDefinition;
import com.placy.placycore.core.processes.data.ParamValueDefinitionInfo;
import com.placy.placycore.core.processes.data.ProcessStepDefinition;
import com.placy.placycore.core.processes.data.ProcessStepDefinitionInfo;
import com.placy.placycore.core.processes.exceptions.TaskNotFoundException;
import com.placy.placycore.core.processes.model.DelegatingTaskParameterValueModel;
import com.placy.placycore.core.processes.model.PredefinedTaskParameterValueModel;
import com.placy.placycore.core.processes.model.ProcessModel;
import com.placy.placycore.core.processes.model.ProcessStepModel;
import com.placy.placycore.core.processes.model.TaskModel;
import com.placy.placycore.core.processes.services.TasksService;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Component
public class ProcessStepDefinitionToProcessStepModelPopulator implements Populator<ProcessStepDefinitionInfo, ProcessStepModel> {
    @Autowired
    private TasksService tasksService;

    @Autowired
    private DelegatingParamDefToPredefinedTaskParameterValueModelPopulator delegatingParamDefToPredefinedTaskParameterValueModelPopulator;

    @Autowired
    private ParamValueDefinitionToPredefinedTaskParameterValueModelPopulator paramValueDefinitionToPredefinedTaskParameterValueModelPopulator;

    @Override
    public void populate(ProcessStepDefinitionInfo processStepDefinitionInfo, ProcessStepModel processStepModel) {
        ProcessStepDefinition processStepDefinition = processStepDefinitionInfo.getProcessStepDefinition();

        processStepModel.setCode(processStepDefinition.getCode());
        processStepModel.setOrder(processStepDefinitionInfo.getOrder());
        processStepModel.setTaskModel(getTask(processStepDefinitionInfo));
        processStepModel.setProcess(getProcess(processStepDefinitionInfo));
        processStepModel.setPredefinedTaskParameterValues(getParamValues(processStepDefinitionInfo, processStepModel));
        processStepModel.setDelegatingTaskParameterValueModels(getDelegatingTaskParameterValues(processStepDefinitionInfo, processStepModel));
    }

    private TaskModel getTask(ProcessStepDefinitionInfo processStepDefinitionInfo) {
        ProcessStepDefinition processStepDefinition = processStepDefinitionInfo.getProcessStepDefinition();
        String taskCode = processStepDefinition.getTaskCode();

        return tasksService.getTaskByCodeOptional(taskCode)
                           .orElseThrow(() -> new TaskNotFoundException(taskCode));
    }

    private ProcessModel getProcess(ProcessStepDefinitionInfo processStepDefinitionInfo) {
        if(processStepDefinitionInfo.getProcessModel() == null) {
            throw new IllegalArgumentException("Process model must be specified");
        }

        return processStepDefinitionInfo.getProcessModel();
    }

    private List<PredefinedTaskParameterValueModel> getParamValues(ProcessStepDefinitionInfo processStepDefinitionInfo,
                                                                   ProcessStepModel processStepModel) {
        List<ParamValueDefinition> paramsValues = processStepDefinitionInfo.getProcessStepDefinition().getParamsValues();

        if(paramsValues == null) {
            paramsValues = new ArrayList<>();
        }

        return paramsValues.stream()
            .map(paramValueDefinition -> getParamValueModel(
                new ParamValueDefinitionInfo(
                    paramValueDefinition, processStepDefinitionInfo, processStepModel
                )
            )).collect(Collectors.toList());
    }

    private PredefinedTaskParameterValueModel getParamValueModel(ParamValueDefinitionInfo paramValueDefinitionInfo) {
        PredefinedTaskParameterValueModel predefinedTaskParameterValueModel = new PredefinedTaskParameterValueModel();
        paramValueDefinitionToPredefinedTaskParameterValueModelPopulator
            .populate(paramValueDefinitionInfo, predefinedTaskParameterValueModel);

        return predefinedTaskParameterValueModel;
    }

    private List<DelegatingTaskParameterValueModel> getDelegatingTaskParameterValues(ProcessStepDefinitionInfo processStepDefinitionInfo, ProcessStepModel processStepModel) {
        ProcessStepDefinition processStepDefinition = processStepDefinitionInfo.getProcessStepDefinition();

        List<DelegatingParamDefinition> delegatingParams = processStepDefinition.getDelegatingParams();

        if(delegatingParams == null) {
            delegatingParams = new ArrayList<>();
        }

        return delegatingParams.stream()
                        .map(delegatingParamDefinition -> getPredefinedTaskParameterValueModel(
                                new DelegatingParamDefinitionInfo(
                                    delegatingParamDefinition, processStepDefinitionInfo, processStepModel
                                )
                            )
                        ).collect(Collectors.toList());
    }

    private DelegatingTaskParameterValueModel getPredefinedTaskParameterValueModel(DelegatingParamDefinitionInfo delegatingParamDefinitionInfo) {
        DelegatingTaskParameterValueModel delegatingTaskParameterValueModel = new DelegatingTaskParameterValueModel();

        delegatingParamDefToPredefinedTaskParameterValueModelPopulator.populate(delegatingParamDefinitionInfo, delegatingTaskParameterValueModel);

        return delegatingTaskParameterValueModel;
    }
}
