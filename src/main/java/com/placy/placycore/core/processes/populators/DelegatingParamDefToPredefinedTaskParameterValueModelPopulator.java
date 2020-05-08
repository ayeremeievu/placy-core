package com.placy.placycore.core.processes.populators;

import com.placy.placycore.core.populators.Populator;
import com.placy.placycore.core.processes.data.DelegatingParamDefinition;
import com.placy.placycore.core.processes.data.DelegatingParamDefinitionInfo;
import com.placy.placycore.core.processes.data.ProcessStepDefinitionInfo;
import com.placy.placycore.core.processes.model.PredefinedTaskParameterValueModel;
import com.placy.placycore.core.processes.model.ProcessModel;
import com.placy.placycore.core.processes.model.ProcessParameterModel;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Component
public class DelegatingParamDefToPredefinedTaskParameterValueModelPopulator implements Populator<DelegatingParamDefinitionInfo, PredefinedTaskParameterValueModel> {

    @Override
    public void populate(DelegatingParamDefinitionInfo delegatingParamDefinitionInfo,
                         PredefinedTaskParameterValueModel predefinedTaskParameterValueModel) {
        List<ProcessParameterModel> processParams = getProcessParams(delegatingParamDefinitionInfo);

        DelegatingParamDefinition delegatingParamDefinition = delegatingParamDefinitionInfo.getDelegatingParamDefinition();


    }

    private List<ProcessParameterModel> getProcessParams(DelegatingParamDefinitionInfo delegatingParamDefinitionInfo) {
        ProcessStepDefinitionInfo processStepDefinitionInfo = delegatingParamDefinitionInfo.getProcessStepDefinitionInfo();
        ProcessModel processModel = processStepDefinitionInfo.getProcessModel();
        return processModel.getParams();
    }
}
