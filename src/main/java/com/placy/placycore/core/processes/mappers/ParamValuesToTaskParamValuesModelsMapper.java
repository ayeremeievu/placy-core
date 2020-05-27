package com.placy.placycore.core.processes.mappers;

import com.placy.placycore.core.processes.data.ParamValueData;
import com.placy.placycore.core.processes.exceptions.TaskParamNotFoundException;
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
public class ParamValuesToTaskParamValuesModelsMapper {

    public List<TaskParameterValueModel> map(TaskModel taskModel,
                                      TaskInstanceModel taskInstanceModel,
                                      List<ParamValueData> paramValuesData
    ) {
        List<TaskParameterModel> parameterModels = taskModel.getParams();

        return paramValuesData
            .stream()
               .map(paramValueData -> {
                   TaskParameterModel matchingTaskParameterModel =
                       parameterModels.stream()
                          .filter(
                              taskParameterModel ->
                                  taskParameterModel
                                      .getCode()
                                      .equals(
                                          paramValueData
                                              .getCode())
                          )
                          .findAny()
                          .orElseThrow(
                              () -> new TaskParamNotFoundException(
                                  paramValueData
                                      .getCode(),
                                  taskModel
                                      .getCode())
                          );
                   TaskParameterValueModel taskParameterValueModel =
                       new TaskParameterValueModel();

                   taskParameterValueModel.setParameter(matchingTaskParameterModel);
                   taskParameterValueModel.setValue(paramValueData.getValue());
                   taskParameterValueModel.setTaskInstance(taskInstanceModel);

                   return taskParameterValueModel;
               }).collect(Collectors.toList());
    }
}
