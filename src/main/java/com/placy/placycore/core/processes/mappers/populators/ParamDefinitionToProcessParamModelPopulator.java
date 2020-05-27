package com.placy.placycore.core.processes.mappers.populators;

import com.placy.placycore.core.populators.Populator;
import com.placy.placycore.core.processes.data.ParamDefinition;
import com.placy.placycore.core.processes.model.ProcessParameterModel;
import org.springframework.stereotype.Component;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Component
public class ParamDefinitionToProcessParamModelPopulator implements Populator<ParamDefinition, ProcessParameterModel> {

    @Override
    public void populate(ParamDefinition paramDefinition, ProcessParameterModel parameterModel) {
        parameterModel.setCode(paramDefinition.getCode());
        parameterModel.setDefaultValue(paramDefinition.getDefaultValue());
    }
}
