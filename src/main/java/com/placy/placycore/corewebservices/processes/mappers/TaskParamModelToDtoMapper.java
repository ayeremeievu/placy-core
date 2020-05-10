package com.placy.placycore.corewebservices.processes.mappers;

import com.placy.placycore.core.processes.model.TaskParameterModel;
import com.placy.placycore.core.mappers.AbstractSimpleMapper;
import com.placy.placycore.corewebservices.processes.dto.ParamDto;
import org.springframework.stereotype.Component;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Component
public class TaskParamModelToDtoMapper extends AbstractSimpleMapper<TaskParameterModel, ParamDto> {

    @Override
    public ParamDto map(TaskParameterModel taskParameterModel) {
        ParamDto paramDto = new ParamDto();

        paramDto.setCode(taskParameterModel.getCode());
        paramDto.setDefaultValue(taskParameterModel.getDefaultValue());

        return paramDto;
    }
}
