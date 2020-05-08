package com.placy.placycore.core.processes.services;

import com.placy.placycore.core.processes.model.TaskParameterModel;
import com.placy.placycore.core.processes.model.TaskParameterValueModel;
import com.placy.placycore.core.processes.repository.ParameterValueRepository;
import com.placy.placycore.core.processes.repository.ParametersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Component
public class ParametersService {
    @Autowired
    private ParametersRepository parametersRepository;

    @Autowired
    private ParameterValueRepository parameterValueRepository;

    public void saveParameter(TaskParameterModel parameterModel) {
        parametersRepository.save(parameterModel);
    }

    public void saveParameterValue(TaskParameterValueModel parameterValueModel) {
        parameterValueRepository.save(parameterValueModel);
    }
}
