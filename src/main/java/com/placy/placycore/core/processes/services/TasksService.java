package com.placy.placycore.core.processes.services;

import com.placy.placycore.core.processes.data.TaskInstanceData;
import com.placy.placycore.core.processes.exceptions.BusinessException;
import com.placy.placycore.core.processes.executable.TaskRunner;
import com.placy.placycore.core.processes.mappers.ParamValuesToMapMapper;
import com.placy.placycore.core.processes.data.ParamValueData;
import com.placy.placycore.core.processes.data.RunTaskData;
import com.placy.placycore.core.processes.exceptions.TaskNotFoundException;
import com.placy.placycore.core.processes.mappers.ParamValuesToTaskParamValuesModelsMapper;
import com.placy.placycore.core.processes.mappers.TaskInstanceModelToDataMapper;
import com.placy.placycore.core.processes.model.TaskInstanceModel;
import com.placy.placycore.core.processes.model.TaskInstanceStatusEnum;
import com.placy.placycore.core.processes.model.TaskModel;
import com.placy.placycore.core.processes.model.TaskParameterModel;
import com.placy.placycore.core.processes.model.TaskParameterValueModel;
import com.placy.placycore.core.processes.repository.TaskInstancesRepository;
import com.placy.placycore.core.processes.repository.TasksRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Component
public class TasksService {
    Logger LOG = LoggerFactory.getLogger(TasksService.class);

    @Autowired
    private TasksRepository tasksRepository;

    @Autowired
    private ParamValuesToMapMapper paramValuesToMapMapper;

    @Autowired
    private ParamValuesToTaskParamValuesModelsMapper paramValuesToTaskParamValuesModelsMapper;

    @Autowired
    private TaskInstanceModelToDataMapper taskInstanceModelToDataMapper;

    @Autowired
    private BeanRunnerService beanRunnerService;

    @Autowired
    private TaskInstancesRepository taskInstancesRepository;

    public Future<?> runTask(RunTaskData runTaskData) {
        TaskRunner taskRunnable = getTaskRunnable(runTaskData);

        return beanRunnerService.runTaskBean(taskRunnable);
    }

    public TaskRunner getTaskRunnable(RunTaskData runTaskData) {
        String taskCode = runTaskData.getCode();

        TaskModel taskModel = tasksRepository.findFirstByCode(taskCode)
                                             .orElseThrow(() -> new TaskNotFoundException(taskCode));

        List<ParamValueData> paramValues = runTaskData.getParamValues();

        return getTaskRunnable(taskModel, paramValues);
    }

    public TaskRunner getTaskRunnable(TaskModel taskModel, List<ParamValueData> paramValues) {
        TaskInstanceModel taskInstanceModel = createTaskInstanceModel(taskModel, paramValues);

        persistInstance(taskInstanceModel);

        return doGetTaskRunnable(taskModel, taskInstanceModel);
    }

    public TaskRunner doGetTaskRunnable(TaskModel taskModel, TaskInstanceModel taskInstanceModel) {
        String runnerBean = taskModel.getRunnerBean();
        return beanRunnerService.getTaskRunner(taskInstanceModel, runnerBean);
    }

    public TaskInstanceModel createTaskInstanceModel(TaskModel taskModel, List<ParamValueData> paramValues) {
        TaskInstanceModel taskInstanceModel = new TaskInstanceModel();
        populateTaskInstance(taskInstanceModel, taskModel, paramValues);

        LOG.info("New Task instance with code {} for task with {} instantiated.", taskInstanceModel.getCode(), taskModel.getCode());

        return taskInstanceModel;
    }

    public void persistInstance(TaskInstanceModel taskInstanceModel) {
        save(taskInstanceModel);
    }

    private void populateTaskInstance(TaskInstanceModel taskInstanceModel, TaskModel taskModel, List<ParamValueData> paramValues) {
        taskInstanceModel.setTask(taskModel);
        taskInstanceModel.setStatus(TaskInstanceStatusEnum.NOT_STARTED);

        List<TaskParameterValueModel> taskParameterValueModels = getTaskParameterValueModels(taskInstanceModel, taskModel, paramValues);

        validateParams(taskInstanceModel, taskParameterValueModels);

        taskInstanceModel.setParamValues(taskParameterValueModels);
    }

    private void validateParams(TaskInstanceModel taskInstanceModel,
                                List<TaskParameterValueModel> taskParameterValueModels) {
        List<TaskParameterModel> params = taskInstanceModel.getTask().getParams();

        params.stream()
              .filter(TaskParameterModel::isRequired)
              .filter(taskParameterModel -> paramValueIsNotDeclared(taskParameterValueModels, taskParameterModel))
              .findAny()
              .ifPresent((taskParameterModel) -> { throw new BusinessException(
                      String.format("Value for parameter : '%s' is not declared", taskParameterModel.getCode())
                  );
              });
    }

    private boolean paramValueIsNotDeclared(List<TaskParameterValueModel> taskParameterValueModels,
                                            TaskParameterModel taskParameterModel) {
        return !isValueDeclared(taskParameterValueModels, taskParameterModel);
    }

    private boolean isValueDeclared(List<TaskParameterValueModel> taskParameterValueModels,
                                    TaskParameterModel taskParameterModel) {
        return taskParameterValueModels.stream()
                                       .anyMatch(taskParameterValueModel -> taskParameterValueModel.getParameter().getCode()
                                                                                                   .equals(taskParameterModel.getCode()));
    }

    private List<TaskParameterValueModel> getTaskParameterValueModels(TaskInstanceModel taskInstanceModel,
                                                                      TaskModel taskModel,
                                                                      List<ParamValueData> paramValues) {
        List<TaskParameterValueModel> allParameters = new ArrayList<>();

        List<TaskParameterValueModel> taskParameterValueModels = paramValuesToTaskParamValuesModelsMapper.map(
            taskModel,
            taskInstanceModel,
            paramValues
        );

        List<TaskParameterValueModel> valuesWithDefaultValue =
            getValuesWithDefaultValue(taskParameterValueModels, taskInstanceModel, taskModel);

        allParameters.addAll(taskParameterValueModels);
        allParameters.addAll(valuesWithDefaultValue);

        return allParameters;
    }

    private List<TaskParameterValueModel> getValuesWithDefaultValue(List<TaskParameterValueModel> taskParameterValueModels, TaskInstanceModel taskInstanceModel, TaskModel taskModel) {
        List<TaskParameterModel> params = taskModel.getParams();

        return params.stream()
              .filter(taskParameterModel -> defaultValueShouldBeAdded(taskParameterValueModels, taskParameterModel))
              .map(taskParameterModel -> createParamValueWithDefaultValue(taskInstanceModel, taskParameterModel))
              .collect(Collectors.toList());
    }

    private TaskParameterValueModel createParamValueWithDefaultValue(TaskInstanceModel taskInstanceModel, TaskParameterModel taskParameterModel) {
        TaskParameterValueModel taskParameterValueModel = new TaskParameterValueModel();

        taskParameterValueModel.setParameter(taskParameterModel);
        taskParameterValueModel.setTaskInstance(taskInstanceModel);
        taskParameterValueModel.setValue(taskParameterModel.getDefaultValue());

        return taskParameterValueModel;
    }

    private boolean defaultValueShouldBeAdded(List<TaskParameterValueModel> taskParameterValueModels,
                                              TaskParameterModel taskParameterModel) {
        return !isParameterValueSpecified(taskParameterValueModels, taskParameterModel) && hasParameterDefaultValue(taskParameterModel);
    }

    private boolean hasParameterDefaultValue(TaskParameterModel taskParameterModel) {
        return taskParameterModel.getDefaultValue() != null;
    }

    private boolean isParameterValueSpecified(List<TaskParameterValueModel> taskParameterValueModels, TaskParameterModel taskParameterModel) {
        Optional<TaskParameterValueModel> valueForParameter = findValueForParameter(taskParameterValueModels, taskParameterModel);

        return valueForParameter.isPresent();
    }

    private Optional<TaskParameterValueModel> findValueForParameter(List<TaskParameterValueModel> taskParameterValueModels,
                                                                    TaskParameterModel taskParameterModel) {
        return taskParameterValueModels.stream()
                                .filter(taskParameterValueModel -> taskParameterValueModel.getParameter().getCode()
                                        .equals(taskParameterModel.getCode())
                                ).findFirst();
    }

    public List<TaskInstanceData> getAllTaskInstances() {
        List<TaskInstanceModel> taskInstanceModels = taskInstancesRepository.findAll();

        return taskInstanceModelToDataMapper.mapAll(taskInstanceModels);
    }

    public void save(TaskInstanceModel taskInstanceModel) {
        taskInstancesRepository.save(taskInstanceModel);
    }

    public void save(TaskModel taskModel) {
        tasksRepository.save(taskModel);
    }

    public void saveAll(List<TaskModel> taskModelList) {
        tasksRepository.saveAll(taskModelList);
    }

    public Optional<TaskModel> getTaskByCodeOptional(String code) {
        return tasksRepository.findFirstByCode(code);
    }

    public Optional<TaskInstanceModel> getTaskInstanceModelByCode(String code) {
        return taskInstancesRepository.findFirstByCode(code);
    }


    public Optional<TaskInstanceModel> getTaskInstanceModelByPk(String pk) {
        return taskInstancesRepository.findById(pk);
    }

    public List<TaskModel> getAllTasks() {
        return tasksRepository.findAll();
    }

    public void remove(TaskModel obsoleteTask) {
        tasksRepository.delete(obsoleteTask);
    }

    public void removeAll(List<TaskModel> obsoleteTasks) {
        tasksRepository.deleteAll(obsoleteTasks);
    }
}
