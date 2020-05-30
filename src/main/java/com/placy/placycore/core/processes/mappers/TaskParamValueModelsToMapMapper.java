package com.placy.placycore.core.processes.mappers;

import com.placy.placycore.core.mappers.AbstractSimpleMapper;
import com.placy.placycore.core.processes.data.ParamValueData;
import com.placy.placycore.core.processes.model.TaskParameterValueModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ayeremeiev@netconomy.net
 */
@Component
public class TaskParamValueModelsToMapMapper extends AbstractSimpleMapper<List<TaskParameterValueModel>, Map<String, Object>> {

    @Override
    public Map<String, Object> map(List<TaskParameterValueModel> paramValues) {
        return paramValues.stream()
                          .collect(Collectors.toMap(
                              paramValue -> paramValue.getParameter().getCode(), TaskParameterValueModel::getValue)
                          );
    }
}
