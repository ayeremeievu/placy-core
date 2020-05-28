package com.placy.placycore.core.processes.services;

import com.placy.placycore.core.processes.data.TaskInstanceData;
import com.placy.placycore.core.processes.mappers.ParamValuesToMapMapper;
import com.placy.placycore.core.processes.data.ParamValueData;
import com.placy.placycore.core.processes.data.RunTaskData;
import com.placy.placycore.core.processes.exceptions.TaskNotFoundException;
import com.placy.placycore.core.processes.mappers.ParamValuesToTaskParamValuesModelsMapper;
import com.placy.placycore.core.processes.mappers.TaskInstanceModelToDataMapper;
import com.placy.placycore.core.processes.model.TaskInstanceModel;
import com.placy.placycore.core.processes.model.TaskInstanceStatusEnum;
import com.placy.placycore.core.processes.model.TaskModel;
import com.placy.placycore.core.processes.model.TaskParameterValueModel;
import com.placy.placycore.core.processes.repository.TaskInstancesRepository;
import com.placy.placycore.core.processes.repository.TasksRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    public void runTask(RunTaskData runTaskData) {
        String taskCode = runTaskData.getCode();

        TaskModel taskModel = tasksRepository.findFirstByCode(taskCode)
                                             .orElseThrow(() -> new TaskNotFoundException(taskCode));

        String runnerBean = taskModel.getRunnerBean();
        Map<String, Object> paramsMap = paramValuesToMapMapper.map(runTaskData.getParamValues());

        TaskInstanceModel taskInstanceModel = new TaskInstanceModel();
        persistInstance(taskInstanceModel, taskModel, runTaskData.getParamValues());

        beanRunnerService.runTaskBean(taskInstanceModel, runnerBean, paramsMap);

        LOG.info("New Task instance with pk {} for task with {} instantiated.", taskInstanceModel.getPk(), taskModel.getCode());
    }

    public void persistInstance(TaskInstanceModel taskInstanceModel,
                                TaskModel taskModel,
                                List<ParamValueData> paramValues) {
        taskInstanceModel.setTask(taskModel);
        taskInstanceModel.setStatus(TaskInstanceStatusEnum.NOT_STARTED);

        List<TaskParameterValueModel> taskParameterValueModels = paramValuesToTaskParamValuesModelsMapper.map(
            taskModel,
            taskInstanceModel,
            paramValues
        );

        taskInstanceModel.setParamValues(taskParameterValueModels);

        save(taskInstanceModel);
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

    public List<TaskModel> getAllTasks() {
        return tasksRepository.findAll();
    }
}
