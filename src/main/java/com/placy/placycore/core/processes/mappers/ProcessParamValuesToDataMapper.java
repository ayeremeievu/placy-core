package com.placy.placycore.core.processes.mappers;

import com.placy.placycore.core.mappers.AbstractSimpleMapper;
import com.placy.placycore.core.processes.data.ParamValueData;
import com.placy.placycore.core.processes.model.ProcessParameterValueModel;
import org.springframework.stereotype.Component;

/**
 * @author ayeremeiev@netconomy.net
 */
@Component
public class ProcessParamValuesToDataMapper extends AbstractSimpleMapper<ProcessParameterValueModel, ParamValueData> {

    @Override
    public ParamValueData map(ProcessParameterValueModel processParameterValueModel) {
        ParamValueData paramValueData = new ParamValueData();

        paramValueData.setCode(processParameterValueModel.getParameter().getCode());
        paramValueData.setValue(processParameterValueModel.getValue());

        return paramValueData;
    }
}
