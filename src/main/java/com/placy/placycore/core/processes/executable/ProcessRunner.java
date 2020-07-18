package com.placy.placycore.core.processes.executable;

import com.placy.placycore.core.processes.data.ParamValueData;
import com.placy.placycore.core.processes.model.DelegatingTaskParameterValueModel;
import com.placy.placycore.core.processes.model.ProcessInstanceModel;
import com.placy.placycore.core.processes.model.ProcessInstanceStatusEnum;
import com.placy.placycore.core.processes.model.ProcessParameterModel;
import com.placy.placycore.core.processes.model.ProcessParameterValueModel;
import com.placy.placycore.core.processes.model.ProcessStepResultEnum;
import com.placy.placycore.core.processes.model.ProcessStepInstanceModel;
import com.placy.placycore.core.processes.model.ProcessStepModel;
import com.placy.placycore.core.processes.model.TaskInstanceModel;
import com.placy.placycore.core.processes.model.TaskInstanceStatusEnum;
import com.placy.placycore.core.processes.model.TaskModel;
import com.placy.placycore.core.processes.model.TaskParameterValueModel;
import com.placy.placycore.core.processes.services.ProcessesService;
import com.placy.placycore.core.processes.services.TasksService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author ayeremeiev@netconomy.net
 */
@Component
@Scope("prototype")
public class ProcessRunner implements Runnable {

    Logger LOG = LoggerFactory.getLogger(ProcessRunner.class);

    private final String processInstanceCode;
    private ProcessInstanceModel processInstanceModel;

    @Autowired
    private ProcessesService processesService;
    @Autowired
    private TasksService tasksService;

    @PersistenceContext
    private EntityManager em;

    public ProcessRunner(String processInstanceCode) {
        this.processInstanceCode = processInstanceCode;
    }

    @PostConstruct
    public void init() {

    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void run() {
        this.processInstanceModel = processesService.getProcessInstanceByCodeOptional(processInstanceCode)
                                                    .orElseThrow(() -> new IllegalStateException("No process instance found for code " + processInstanceCode));

        if (processInstanceModel == null) {
            throw new IllegalStateException("processInstanceModel is null");
        }

        processInstanceModel.setStatus(ProcessInstanceStatusEnum.PREPAIRING);
        processInstanceModel.setStartDate(new Date());

        LOG.info("The process instance with code {} with a process code {} has started",
                 processInstanceModel.getCode(),
                 processInstanceModel.getProcess().getCode()
        );

        try {
            executeSteps(processInstanceModel);
            processInstanceModel.setStatus(ProcessInstanceStatusEnum.DONE);
        } catch (Exception ex) {
            LOG.error("Exception occurred during steps execution: ", ex);
            processInstanceModel.setStatus(ProcessInstanceStatusEnum.ERROR);
        }

        LOG.info("The process instance with code {} with a process code {} has finished",
                 processInstanceModel.getCode(),
                 processInstanceModel.getProcess().getCode()
        );
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
        processInstanceModel.setProcessStepsInstances(new ArrayList<>());

        for (ProcessStepModel processStepModel : processSteps) {
            ProcessStepInstanceModel processStepInstanceModel =
                createAndSaveProcessStepInstanceModel(processInstanceModel, processStepModel);

            LOG.info(String.format(
                "Process step instance with code : %s has started", processStepInstanceModel.getCode()
            ));

            doRunInnerTask(processInstanceModel, processStepModel, processStepInstanceModel);

            processStepInstanceModel.setFinishDate(new Date());

            processInstanceModel.getProcessStepsInstances().add(processStepInstanceModel);
            processesService.save(processStepInstanceModel);

            if (processStepInstanceModel.getProcessStepResult() == ProcessStepResultEnum.NOK) {
                throw new IllegalStateException(interruptedProcessErrorMessage(processStepInstanceModel));
            }
        }
    }

    private String interruptedProcessErrorMessage(ProcessStepInstanceModel processStepInstanceModel) {
        return String.format(
                "Process step instance %s finished with errors. Stopped executing the process",
                processStepInstanceModel.getCode()
            );
    }

    private void doRunInnerTask(ProcessInstanceModel processInstanceModel,
                                ProcessStepModel processStepModel,
                                ProcessStepInstanceModel processStepInstanceModel) {
        TaskModel taskModel = processStepModel.getTaskModel();

        List<ParamValueData> paramValuesData = getParamValuesData(processInstanceModel, processStepModel);

        runInnerTask(taskModel, paramValuesData, processStepInstanceModel);
    }

    private ProcessStepInstanceModel createAndSaveProcessStepInstanceModel(ProcessInstanceModel processInstanceModel,
                                                                           ProcessStepModel processStepModel) {
        ProcessStepInstanceModel processStepInstanceModel = new ProcessStepInstanceModel();

        processStepInstanceModel.setStartDate(new Date());
        processStepInstanceModel.setProcessInstance(processInstanceModel);
        processStepInstanceModel.setProcessStep(processStepModel);

        processesService.save(processStepInstanceModel);
        return processStepInstanceModel;
    }

    private List<ParamValueData> getParamValuesData(ProcessInstanceModel processInstanceModel, ProcessStepModel processStepModel) {
        return processParametersValuesModelsStream(processInstanceModel, processStepModel)
                        .map(this::convertToParamValueData)
                        .collect(Collectors.toList());
    }

    private void runInnerTask(TaskModel taskModel,
                              List<ParamValueData> paramValuesData,
                              ProcessStepInstanceModel processStepInstanceModel) {
        TaskInstanceModel taskInstance = tasksService.createTaskInstanceModel(taskModel, paramValuesData);
        processStepInstanceModel.setTaskInstanceModel(taskInstance);
        processesService.save(processStepInstanceModel);

        TaskRunner taskRunner = tasksService.doGetTaskRunnable(taskModel, taskInstance);

        CompletableFuture<Void> taskRunnerCompletableFuture = CompletableFuture.runAsync(taskRunner);

        try {
            taskRunnerCompletableFuture.get();

            setResult(processStepInstanceModel);
        } catch (InterruptedException | ExecutionException e) {
            LOG.error(e.getMessage());
            e.printStackTrace();

            processStepInstanceModel.setProcessStepResult(ProcessStepResultEnum.NOK);
        }
    }

    private void setResult(ProcessStepInstanceModel processStepInstanceModel) {
        TaskInstanceModel taskInstanceModel = processStepInstanceModel.getTaskInstanceModel();

        TaskInstanceStatusEnum status = taskInstanceModel.getStatus();

        if(status == TaskInstanceStatusEnum.DONE) {
            processStepInstanceModel.setProcessStepResult(ProcessStepResultEnum.OK);
        } else if(status == TaskInstanceStatusEnum.ERROR) {
            processStepInstanceModel.setProcessStepResult(ProcessStepResultEnum.NOK);
        } else {
            processStepInstanceModel.setProcessStepResult(ProcessStepResultEnum.NOK);
            throw new IllegalStateException("Task instance not in a finished state. Its status : " + status);
        }
    }

    private ParamValueData convertToParamValueData(TaskParameterValueModel taskParameterValueModel) {
        ParamValueData paramValueData = new ParamValueData();

        paramValueData.setCode(taskParameterValueModel.getParameter().getCode());
        paramValueData.setValue(taskParameterValueModel.getValue());

        return paramValueData;
    }

    public Stream<TaskParameterValueModel> processParametersValuesModelsStream(ProcessInstanceModel processInstanceModel, ProcessStepModel processStepModel) {
        return Stream.concat(
            getPredefinedParamValuesStream(processStepModel), getDelegatingParamValuesStream(processInstanceModel, processStepModel)
        );
    }

    private Stream<TaskParameterValueModel> getDelegatingParamValuesStream(ProcessInstanceModel processInstanceModel,
                                                                           ProcessStepModel processStepModel) {
        return processStepModel
             .getDelegatingTaskParameterValueModels()
             .stream()
             .map(delegatingTaskParameterValueModel -> {
                 TaskParameterValueModel taskParameterValueModel = new TaskParameterValueModel();

                 taskParameterValueModel.setParameter(delegatingTaskParameterValueModel.getTaskParameter());

                 taskParameterValueModel.setValue(getProcessParameterValueModel(processInstanceModel,
                                                                                delegatingTaskParameterValueModel));

                 return taskParameterValueModel;
             });
    }

    private Stream<TaskParameterValueModel> getPredefinedParamValuesStream(ProcessStepModel processStepModel) {
        return processStepModel.getPredefinedTaskParameterValues()
              .stream()
              .map(predefinedTaskParameterValueModel -> {
                  TaskParameterValueModel taskParameterValueModel = new TaskParameterValueModel();

                  taskParameterValueModel.setParameter(predefinedTaskParameterValueModel.getTaskParameter());
                  taskParameterValueModel.setValue(predefinedTaskParameterValueModel.getValue());

                  return taskParameterValueModel;
              });
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
