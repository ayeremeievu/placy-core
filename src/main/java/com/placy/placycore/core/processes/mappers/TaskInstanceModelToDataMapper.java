package com.placy.placycore.core.processes.mappers;

import com.placy.placycore.core.mappers.AbstractSimpleMapper;
import com.placy.placycore.core.processes.data.TaskInstanceData;
import com.placy.placycore.core.processes.model.TaskInstanceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ayeremeiev@netconomy.net
 */
@Component
public class TaskInstanceModelToDataMapper extends AbstractSimpleMapper<TaskInstanceModel, TaskInstanceData> {
    @Autowired
    private TaskParamValueModelToDataMapper taskParamValueModelToDataMapper;

    @Override
    public TaskInstanceData map(TaskInstanceModel taskInstanceModel) {
        TaskInstanceData taskInstanceData = new TaskInstanceData();

        taskInstanceData.setTaskCode(taskInstanceModel.getTask().getCode());
        taskInstanceData.setParams(taskParamValueModelToDataMapper.mapAll(taskInstanceModel.getParamValues()));
        taskInstanceData.setStatus(taskInstanceModel.getStatus());
        taskInstanceData.setStartDate(taskInstanceModel.getStartDate());
        taskInstanceData.setFinishDate(taskInstanceModel.getFinishDate());

        return taskInstanceData;
    }
}
