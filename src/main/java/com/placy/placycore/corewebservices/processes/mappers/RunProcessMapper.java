package com.placy.placycore.corewebservices.processes.mappers;

import com.placy.placycore.core.processes.data.RunProcessData;
import com.placy.placycore.corewebservices.processes.dto.RunProcessDto;
import org.mapstruct.Mapper;

/**
 * @author ayeremeiev@netconomy.net
 */
@Mapper(componentModel = "spring", uses = {ParamValueMapper.class})
public interface RunProcessMapper {
    RunProcessData map(RunProcessDto runProcessDto);
}
