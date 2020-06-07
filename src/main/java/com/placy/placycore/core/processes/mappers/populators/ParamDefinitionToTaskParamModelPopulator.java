package com.placy.placycore.core.processes.mappers.populators;

import com.placy.placycore.core.populators.Populator;
import com.placy.placycore.core.processes.data.ParamDefinition;
import com.placy.placycore.core.processes.model.TaskParameterModel;
import org.springframework.stereotype.Component;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Component
public class ParamDefinitionToTaskParamModelPopulator implements Populator<ParamDefinition, TaskParameterModel> {

    @Override
    public void populate(ParamDefinition paramDefinition, TaskParameterModel parameterModel) {
        parameterModel.setCode(paramDefinition.getCode());
        parameterModel.setDefaultValue(paramDefinition.getDefaultValue());
        parameterModel.setRequired(paramDefinition.isRequired());
    }
}
