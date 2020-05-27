package com.placy.placycore.corewebservices.processes.mappers;

import com.placy.placycore.core.processes.data.ParamValueData;
import com.placy.placycore.corewebservices.processes.dto.ParamValueDto;
import org.mapstruct.Mapper;

/**
 * @author ayeremeiev@netconomy.net
 */
@Mapper(componentModel = "spring")
public interface ParamValueMapper {
    ParamValueData paramValueDataToDto(ParamValueDto paramValueDto);
}
