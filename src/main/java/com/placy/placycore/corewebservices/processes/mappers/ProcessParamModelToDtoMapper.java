package com.placy.placycore.corewebservices.processes.mappers;

import com.placy.placycore.core.mappers.AbstractSimpleMapper;
import com.placy.placycore.core.processes.model.ProcessParameterModel;
import com.placy.placycore.corewebservices.processes.dto.ParamDto;
import org.springframework.stereotype.Component;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Component
public class ProcessParamModelToDtoMapper extends AbstractSimpleMapper<ProcessParameterModel, ParamDto> {

    @Override
    public ParamDto map(ProcessParameterModel processParameterModel) {
        ParamDto paramDto = new ParamDto();
        paramDto.setDefaultValue(processParameterModel.getDefaultValue());
        paramDto.setCode(processParameterModel.getCode());

        return paramDto;
    }
}
