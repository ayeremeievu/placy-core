package com.placy.placycore.corewebservices.processes.mappers;

import com.placy.placycore.core.processes.data.TaskInstanceData;
import com.placy.placycore.corewebservices.processes.dto.TaskInstanceDto;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author ayeremeiev@netconomy.net
 */
@Mapper(componentModel = "spring", uses = {ParamValueMapper.class})
public interface TaskInstanceMapper {
    TaskInstanceDto taskInstanceDataToDto(TaskInstanceData taskInstanceData);
    List<TaskInstanceDto> taskInstancesDataToDtos(List<TaskInstanceData> taskInstanceData);
}
