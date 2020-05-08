package com.placy.placycore.core.processes.populators;

import com.placy.placycore.core.populators.Populator;
import com.placy.placycore.core.processes.data.DelegatingParamDefinition;
import com.placy.placycore.core.processes.data.DelegatingParamDefinitionInfo;
import com.placy.placycore.core.processes.data.ParamValueDefinition;
import com.placy.placycore.core.processes.data.ProcessStepDefinition;
import com.placy.placycore.core.processes.data.ProcessStepDefinitionInfo;
import com.placy.placycore.core.processes.model.PredefinedTaskParameterValueModel;
import com.placy.placycore.core.processes.model.ProcessStepModel;
import com.placy.placycore.core.processes.model.TaskModel;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Component
public class ProcessStepDefinitionToProcessStepModelPopulator implements Populator<ProcessStepDefinitionInfo, ProcessStepModel> {
    @Autowired
    private DelegatingParamDefToPredefinedTaskParameterValueModelPopulator delegatingParamDefToPredefinedTaskParameterValueModelPopulator;

    @Override
    public void populate(ProcessStepDefinitionInfo processStepDefinitionInfo, ProcessStepModel processStepModel) {
        ProcessStepDefinition processStepDefinition = processStepDefinitionInfo.getProcessStepDefinition();

        processStepModel.setCode(processStepDefinition.getCode());
        processStepModel.setTaskModel(getTask(processStepDefinitionInfo));
        processStepModel.setPredefinedTaskParameterValues(getDelegatingTaskParameterValues(processStepDefinitionInfo, processStepModel));

        validateProcessStep(processStepModel);
    }

    private TaskModel getTask(ProcessStepDefinitionInfo processStepDefinitionInfo) {
        throw new NotImplementedException("Not implemented yet");
    }

    private List<PredefinedTaskParameterValueModel> getPredefinedTaskParameterValues(ProcessStepDefinitionInfo processStepDefinitionInfo, ProcessStepModel processStepModel) {
        List<PredefinedTaskParameterValueModel> predefinedTaskParameterValues = Stream.concat(
            getParamValues(processStepDefinitionInfo, processStepModel).stream(),
            getDelegatingTaskParameterValues(processStepDefinitionInfo, processStepModel).stream()
        ).collect(Collectors.toList());

        return predefinedTaskParameterValues;
    }

    private List<PredefinedTaskParameterValueModel> getParamValues(ProcessStepDefinitionInfo processStepDefinitionInfo,
                                                                   ProcessStepModel processStepModel) {
        List<ParamValueDefinition> paramsValues = processStepDefinitionInfo.getProcessStepDefinition().getParamsValues();

        throw new NotImplementedException("Not implemented yet");
    }

    private void validateProcessStep(ProcessStepModel processStepModel) {
        throw new NotImplementedException("Not implemented yet");
    }

    private List<PredefinedTaskParameterValueModel> getDelegatingTaskParameterValues(ProcessStepDefinitionInfo processStepDefinitionInfo, ProcessStepModel processStepModel) {
        ProcessStepDefinition processStepDefinition = processStepDefinitionInfo.getProcessStepDefinition();

        List<DelegatingParamDefinition> delegatingParams = processStepDefinition.getDelegatingParams();

        return delegatingParams.stream()
                        .map(delegatingParamDefinition -> getPredefinedTaskParameterValueModel(
                                new DelegatingParamDefinitionInfo(
                                    delegatingParamDefinition, processStepDefinitionInfo, processStepModel
                                )
                            )
                        ).collect(Collectors.toList());
    }

    private PredefinedTaskParameterValueModel getPredefinedTaskParameterValueModel(DelegatingParamDefinitionInfo delegatingParamDefinitionInfo) {
        PredefinedTaskParameterValueModel predefinedTaskParameterValueModel = new PredefinedTaskParameterValueModel();

        delegatingParamDefToPredefinedTaskParameterValueModelPopulator.populate(delegatingParamDefinitionInfo, predefinedTaskParameterValueModel);

        return predefinedTaskParameterValueModel;
    }
}
