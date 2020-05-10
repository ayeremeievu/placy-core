package com.placy.placycore.corewebservices.processes.mappers;

import com.placy.placycore.core.mappers.AbstractSimpleMapper;
import com.placy.placycore.core.processes.model.ProcessModel;
import com.placy.placycore.corewebservices.processes.dto.ProcessDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Component
public class ProcessModelToDtoMapper extends AbstractSimpleMapper<ProcessModel, ProcessDto> {
    @Autowired
    private ProcessParamModelToDtoMapper processParamModelToDtoMapper;

    @Override
    public ProcessDto map(ProcessModel processModel) {
        ProcessDto processDto = new ProcessDto();

        processDto.setCode(processModel.getCode());
        processDto.setName(processModel.getName());
        processDto.setParams(processParamModelToDtoMapper.mapAll(processModel.getParams()));

        return processDto;
    }
}
