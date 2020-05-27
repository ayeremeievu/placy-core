package com.placy.placycore.core.processes.mappers;

import com.placy.placycore.core.mappers.AbstractSimpleMapper;
import com.placy.placycore.core.processes.data.ParamValueData;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ayeremeiev@netconomy.net
 */
@Component
public class ParamValuesToMapMapper extends AbstractSimpleMapper<List<ParamValueData>, Map<String, Object>> {

    @Override
    public Map<String, Object> map(List<ParamValueData> paramValues) {
        return paramValues.stream()
            .collect(Collectors.toMap(ParamValueData::getCode, ParamValueData::getValue));
    }
}
