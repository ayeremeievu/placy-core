package com.placy.placycore.core.processes.services;

import com.placy.placycore.core.processes.data.ParamValueData;
import com.placy.placycore.core.processes.data.ProcessInstanceData;
import com.placy.placycore.core.processes.data.RunProcessData;
import com.placy.placycore.core.processes.exceptions.BusinessException;
import com.placy.placycore.core.processes.exceptions.ProcessNotFoundException;
import com.placy.placycore.core.processes.mappers.ParamValuesToProcessParamValuesModelsMapper;
import com.placy.placycore.core.processes.mappers.ProcessInstanceModelToDataMapper;
import com.placy.placycore.core.processes.model.ProcessInstanceModel;
import com.placy.placycore.core.processes.model.ProcessInstanceStatusEnum;
import com.placy.placycore.core.processes.model.ProcessModel;
import com.placy.placycore.core.processes.model.ProcessParameterModel;
import com.placy.placycore.core.processes.model.ProcessParameterValueModel;
import com.placy.placycore.core.processes.model.ProcessStepInstanceModel;
import com.placy.placycore.core.processes.model.ResourceImportModel;
import com.placy.placycore.core.processes.repository.ProcessInstanceRepository;
import com.placy.placycore.core.processes.repository.ProcessStepInstanceRepository;
import com.placy.placycore.core.processes.repository.ProcessesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Component
public class ProcessesService {
    private final Logger LOG = LoggerFactory.getLogger(ProcessesService.class);

    @Autowired
    private ResourceImportService resourceImportService;

    @Autowired
    private ProcessesRepository processesRepository;

    @Autowired
    private ProcessInstanceRepository processInstanceRepository;

    @Autowired
    private ProcessStepInstanceRepository processStepInstanceRepository;

    @Autowired
    private ParamValuesToProcessParamValuesModelsMapper paramValuesToProcessParamValuesModelsMapper;

    @Autowired
    private ProcessInstanceModelToDataMapper processInstanceModelToDataMapper;

    @Autowired
    private ProcessRunnerService processRunnerService;

    public void save(ProcessModel processModel) {
        processesRepository.save(processModel);
    }

    public Optional<ProcessModel> getLastProcessByCodeOptional(String code) {
        Optional<ResourceImportModel> lastResourceImport = resourceImportService.getLastResourceImport();

        if(lastResourceImport.isPresent()) {
            ResourceImportModel resourceImportModel = lastResourceImport.get();

            Integer version = resourceImportModel.getVersion();
            return processesRepository.getFirstByCodeAndProcessResourceResourceImportVersion(code, version);
        }

        return Optional.empty();
    }

    public List<ProcessModel> getAllLastProcesses() {
        Optional<ResourceImportModel> lastResourceImport = resourceImportService.getLastResourceImport();

        if(lastResourceImport.isPresent()) {
            ResourceImportModel resourceImportModel = lastResourceImport.get();

            Integer version = resourceImportModel.getVersion();
            return processesRepository.getAllByProcessResourceResourceImportVersion(version);
        }

        return new ArrayList<>();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ProcessInstanceModel initializeProcess(RunProcessData runProcessData) {
        String processCode = runProcessData.getProcessCode();

        ProcessModel processModel = getLastProcessByCodeOptional(processCode)
                                             .orElseThrow(() -> new ProcessNotFoundException(processCode));

        ProcessInstanceModel processInstanceModel = new ProcessInstanceModel();
        processInstanceModel = persistInstance(processInstanceModel, processModel, runProcessData.getParamValues());

        LOG.info("New process instance with code {} for task with {} instantiated.", processInstanceModel.getCode(), processModel.getCode());

        processModel.getProcessSteps().size();
        processModel.getParams().size();

        return processInstanceModel;
    }

    public ProcessInstanceModel runProcess(ProcessInstanceModel processInstanceModel) {
        processRunnerService.runProcess(processInstanceModel);

        return processInstanceModel;
    }

    private ProcessInstanceModel persistInstance(ProcessInstanceModel processInstanceModel, ProcessModel processModel, List<ParamValueData> paramValues) {
        processInstanceModel.setProcess(processModel);
        processInstanceModel.setStatus(ProcessInstanceStatusEnum.NOT_STARTED);

        List<ProcessParameterValueModel> processParameterValueModels = paramValuesToProcessParamValuesModelsMapper.map(
            processModel,
            processInstanceModel,
            paramValues
        );

        validateParams(processInstanceModel, processParameterValueModels);

        processInstanceModel.setParamValues(processParameterValueModels);

        return saveAndFlushTransactional(processInstanceModel);
    }

    private void validateParams(ProcessInstanceModel processInstanceModel,
                                List<ProcessParameterValueModel> processParameterValueModels) {
        List<ProcessParameterModel> params = processInstanceModel.getProcess().getParams();

        params.stream()
              .filter(ProcessParameterModel::isRequired)
              .filter(taskParameterModel -> paramValueIsNotDeclared(processParameterValueModels, taskParameterModel))
              .findAny()
              .ifPresent((taskParameterModel) -> { throw new BusinessException(
                             String.format("Value for parameter : '%s' is not declared", taskParameterModel.getCode())
                         );
                         }
              );
    }


    private boolean paramValueIsNotDeclared(List<ProcessParameterValueModel> processParameterValueModels,
                                            ProcessParameterModel processParameterModel) {
        return !isValueDeclared(processParameterValueModels, processParameterModel);
    }

    private boolean isValueDeclared(List<ProcessParameterValueModel> processParameterValueModels,
                                    ProcessParameterModel processParameterModel) {
        return processParameterValueModels.stream()
                                          .anyMatch(taskParameterValueModel -> taskParameterValueModel.getParameter().getCode()
                                                                                                      .equals(processParameterModel.getCode()));
    }

    public List<ProcessInstanceData> getAllProcessInstances() {
        return processInstanceModelToDataMapper.mapAll(getAllProcessInstancesModels());
    }

    public List<ProcessInstanceModel> getAllProcessInstancesModels() {
        return processInstanceRepository.findAll();
    }

    public ProcessInstanceModel saveAndFlush(ProcessInstanceModel processInstanceModel) {
        return processInstanceRepository.saveAndFlush(processInstanceModel);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ProcessInstanceModel saveAndFlushTransactional(ProcessInstanceModel processInstanceModel) {
        return processInstanceRepository.saveAndFlush(processInstanceModel);
    }

    public void save(ProcessInstanceModel processInstanceModel) {
        processInstanceRepository.save(processInstanceModel);
    }

    public void save(ProcessStepInstanceModel processStepInstanceModel) {
        processStepInstanceRepository.save(processStepInstanceModel);
    }

    public Optional<ProcessInstanceModel> getProcessInstanceByCodeOptional(String processInstanceCode) {
        return processInstanceRepository.getFirstByCode(processInstanceCode);
    }

    public void remove(ProcessModel obsoleteProcess) {
        processesRepository.delete(obsoleteProcess);
    }

    public void removeAll(List<ProcessModel> obsoleteProcesses) {
        processesRepository.deleteAll(obsoleteProcesses);
    }
}
