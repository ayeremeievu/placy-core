package com.placy.placycore.core.processes.executable;

import com.placy.placycore.core.processes.data.ParamValueData;
import com.placy.placycore.core.processes.model.DelegatingTaskParameterValueModel;
import com.placy.placycore.core.processes.model.ProcessInstanceModel;
import com.placy.placycore.core.processes.model.ProcessInstanceStatusEnum;
import com.placy.placycore.core.processes.model.ProcessParameterModel;
import com.placy.placycore.core.processes.model.ProcessParameterValueModel;
import com.placy.placycore.core.processes.model.ProcessStepInstanceModel;
import com.placy.placycore.core.processes.model.ProcessStepModel;
import com.placy.placycore.core.processes.model.TaskModel;
import com.placy.placycore.core.processes.model.TaskParameterValueModel;
import com.placy.placycore.core.processes.services.ProcessesService;
import com.placy.placycore.core.processes.services.TasksService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ayeremeiev@netconomy.net
 */
@Component
@Scope("prototype")
public class ProcessRunner implements Runnable {

    Logger LOG = LoggerFactory.getLogger(ProcessRunner.class);

    private ProcessInstanceModel processInstanceModel;

    @Autowired
    private ProcessesService processesService;
    @Autowired
    private TasksService tasksService;

    public ProcessRunner(ProcessInstanceModel processInstanceModel) {
        this.processInstanceModel = processInstanceModel;
    }

    @Override
    public void run() {
        if (processInstanceModel == null) {
            throw new IllegalStateException("processInstanceModel is null");
        }

        processInstanceModel.setStatus(ProcessInstanceStatusEnum.PREPAIRING);
        processInstanceModel.setStartDate(new Date());

        LOG.info("The process instance with pk {} with a process code {} has started",
                 processInstanceModel.getPk(),
                 processInstanceModel.getProcess().getCode()
        );

        executeSteps(processInstanceModel);

        LOG.info("The task instance with pk {} with a task code {} has finished",
                 processInstanceModel.getPk(),
                 processInstanceModel.getProcess().getCode()
        );
        processInstanceModel.setStatus(ProcessInstanceStatusEnum.DONE);
        processInstanceModel.setFinishDate(new Date());
        processesService.save(processInstanceModel);
    }

    private void executeSteps(ProcessInstanceModel processInstanceModel) {
        List<ProcessStepModel> processSteps = processInstanceModel.getProcess().getProcessSteps();

        if (processSteps != null && !processSteps.isEmpty()) {
            doExecuteSteps(processInstanceModel, processSteps);
        }
    }

    private void doExecuteSteps(ProcessInstanceModel processInstanceModel, List<ProcessStepModel> processSteps) {
        processSteps.forEach(
            processStepModel -> {
                ProcessStepInstanceModel processStepInstanceModel = new ProcessStepInstanceModel();

                processStepInstanceModel.setStartDate(new Date());
                processStepInstanceModel.setProcessInstance(processInstanceModel);
                processStepInstanceModel.setProcessStep(processStepModel);

                processesService.save(processStepInstanceModel);

                TaskModel taskModel = processStepModel.getTaskModel();

                List<ParamValueData> paramValuesData = processParametersValuesModelsStream(processInstanceModel, processStepModel)
                    .map(this::convertToParamValueData)
                    .collect(Collectors.toList());

                tasksService.runTask(taskModel, paramValuesData);
                // TODO Wait until task finishes;
            }
        );
    }

    private ParamValueData convertToParamValueData(TaskParameterValueModel taskParameterValueModel) {
        ParamValueData paramValueData = new ParamValueData();

        paramValueData.setCode(taskParameterValueModel.getParameter().getCode());
        paramValueData.setValue(taskParameterValueModel.getValue());

        return paramValueData;
    }

    public Stream<TaskParameterValueModel> processParametersValuesModelsStream(ProcessInstanceModel processInstanceModel, ProcessStepModel processStepModel) {
        return Stream.concat(processStepModel
                          .getPredefinedTaskParameterValues()
                          .stream()
                          .map(predefinedTaskParameterValueModel -> {
                              TaskParameterValueModel taskParameterValueModel = new TaskParameterValueModel();

                              taskParameterValueModel.setParameter(predefinedTaskParameterValueModel.getTaskParameter());
                              taskParameterValueModel.setValue(predefinedTaskParameterValueModel.getValue());

                              return taskParameterValueModel;
                          }),
                      processStepModel
                          .getDelegatingTaskParameterValueModels()
                          .stream()
                          .map(delegatingTaskParameterValueModel -> {
                              TaskParameterValueModel taskParameterValueModel = new TaskParameterValueModel();

                              taskParameterValueModel.setParameter(delegatingTaskParameterValueModel.getTaskParameter());

                              taskParameterValueModel.setValue(getProcessParameterValueModel(processInstanceModel,
                                                                                             delegatingTaskParameterValueModel));

                              return taskParameterValueModel;
                          })
        );
    }

    private String getProcessParameterValueModel(ProcessInstanceModel processInstanceModel,
                                                 DelegatingTaskParameterValueModel delegatingTaskParameterValueModel) {
        List<ProcessParameterValueModel> paramValues = processInstanceModel.getParamValues();
        ProcessParameterModel processParameterModel = delegatingTaskParameterValueModel.getProcessParameterModel();

        return paramValues.stream()
                          .filter(curProcessParameterValueModel -> curProcessParameterValueModel.getParameter().getCode()
                                                                                                .equals(processParameterModel.getCode())
                          ).findFirst()
                          .orElseThrow(() -> new IllegalStateException(
                              String.format("The process instance %s process doesn't have parameter %s required for delegating"
                                                + " parameter", processInstanceModel.getCode(), processParameterModel.getCode()
                              ))
                          ).getValue();
    }
}
