package com.placy.placycore.core.processes.mappers;

import com.placy.placycore.core.processes.data.ParamValueData;
import com.placy.placycore.core.processes.exceptions.TaskParamNotFoundException;
import com.placy.placycore.core.processes.model.ProcessInstanceModel;
import com.placy.placycore.core.processes.model.ProcessModel;
import com.placy.placycore.core.processes.model.ProcessParameterModel;
import com.placy.placycore.core.processes.model.ProcessParameterValueModel;
import com.placy.placycore.core.processes.model.TaskInstanceModel;
import com.placy.placycore.core.processes.model.TaskModel;
import com.placy.placycore.core.processes.model.TaskParameterModel;
import com.placy.placycore.core.processes.model.TaskParameterValueModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ayeremeiev@netconomy.net
 */
@Component
public class ParamValuesToProcessParamValuesModelsMapper {

    public List<ProcessParameterValueModel> map(ProcessModel processModel,
                                                ProcessInstanceModel processInstanceModel,
                                                List<ParamValueData> paramValuesData
    ) {
        List<ProcessParameterModel> parameterModels = processModel.getParams();

        return paramValuesData
            .stream()
               .map(paramValueData -> {
                   ProcessParameterModel matchingTaskParameterModel =
                       parameterModels.stream()
                          .filter(processParameterModel -> processParameterModel.getCode().equals(paramValueData.getCode()))
                          .findAny()
                          .orElseThrow(() -> new TaskParamNotFoundException(
                                  paramValueData.getCode(),
                                  processModel.getCode()
                              )
                          );

                   ProcessParameterValueModel processParameterValueModel =
                       new ProcessParameterValueModel();

                   processParameterValueModel.setParameter(matchingTaskParameterModel);
                   processParameterValueModel.setValue(paramValueData.getValue());
                   processParameterValueModel.setProcessInstance(processInstanceModel);

                   return processParameterValueModel;
               }).collect(Collectors.toList());
    }
}
