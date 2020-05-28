package com.placy.placycore.core.processes.mappers;

import com.placy.placycore.core.mappers.AbstractSimpleMapper;
import com.placy.placycore.core.processes.data.ParamValueData;
import com.placy.placycore.core.processes.model.TaskParameterModel;
import com.placy.placycore.core.processes.model.TaskParameterValueModel;
import org.springframework.stereotype.Component;

/**
 * @author ayeremeiev@netconomy.net
 */
@Component
public class TaskParamValueModelToDataMapper extends AbstractSimpleMapper<TaskParameterValueModel, ParamValueData> {

    @Override
    public ParamValueData map(TaskParameterValueModel taskParameterValueModel) {
        ParamValueData paramValueData = new ParamValueData();

        paramValueData.setCode(taskParameterValueModel.getParameter().getCode());
        paramValueData.setValue(taskParameterValueModel.getValue());

        return paramValueData;
    }
}
