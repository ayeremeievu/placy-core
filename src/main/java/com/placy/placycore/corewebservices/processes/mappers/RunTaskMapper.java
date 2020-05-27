package com.placy.placycore.corewebservices.processes.mappers;

import com.placy.placycore.core.processes.data.RunTaskData;
import com.placy.placycore.corewebservices.processes.dto.RunTaskDto;
import org.mapstruct.Mapper;

/**
 * @author ayeremeiev@netconomy.net
 */
@Mapper(componentModel = "spring", uses = {ParamValueMapper.class})
public interface RunTaskMapper {
    RunTaskData runTaskDtoToData(RunTaskDto runTaskDto);
}
