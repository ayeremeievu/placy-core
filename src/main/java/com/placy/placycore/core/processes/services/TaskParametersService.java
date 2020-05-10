package com.placy.placycore.core.processes.services;

import com.placy.placycore.core.processes.model.TaskParameterModel;
import com.placy.placycore.core.processes.model.TaskParameterValueModel;
import com.placy.placycore.core.processes.repository.TaskParameterValueRepository;
import com.placy.placycore.core.processes.repository.TaskParametersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Component
public class TaskParametersService {
    @Autowired
    private TaskParametersRepository taskParametersRepository;

    @Autowired
    private TaskParameterValueRepository taskParameterValueRepository;

    public void saveParameter(TaskParameterModel parameterModel) {
        taskParametersRepository.save(parameterModel);
    }

    public void saveParameterValue(TaskParameterValueModel parameterValueModel) {
        taskParameterValueRepository.save(parameterValueModel);
    }

    public Optional<TaskParameterModel> getTaskParameterOptional(String paramCode, String taskCode) {
        return taskParametersRepository.getFirstByCodeAndTaskCode(paramCode, taskCode);
    }
}
