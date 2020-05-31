package com.placy.placycore.corewebservices.processes.mappers;

import com.placy.placycore.core.processes.data.ProcessStepInstanceData;
import com.placy.placycore.corewebservices.processes.dto.ProcessStepInstanceDto;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author ayeremeiev@netconomy.net
 */
@Mapper(componentModel = "spring")
public interface ProcessStepInstanceMapper {
    ProcessStepInstanceDto processStepInstanceDataToDto(ProcessStepInstanceData processStepInstanceData);

    List<ProcessStepInstanceDto> processStepInstanceDataToDtoList(List<ProcessStepInstanceData> processStepInstanceData);
}
