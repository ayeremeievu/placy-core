package com.placy.placycore.corewebservices.processes.mappers;

import com.placy.placycore.core.processes.data.ProcessInstanceData;
import com.placy.placycore.corewebservices.processes.dto.ProcessInstanceDto;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author ayeremeiev@netconomy.net
 */
@Mapper(componentModel = "spring", uses = {ParamValueMapper.class, ProcessStepInstanceMapper.class})
public interface ProcessInstanceMapper {
    ProcessInstanceDto processInstanceDataToDto(ProcessInstanceData processInstanceData);

    List<ProcessInstanceDto> processInstanceDataToDtoList(List<ProcessInstanceData> processInstanceData);
}
