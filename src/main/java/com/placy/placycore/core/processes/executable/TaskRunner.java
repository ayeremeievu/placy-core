package com.placy.placycore.core.processes.executable;

import com.placy.placycore.core.processes.mappers.TaskParamValueModelsToMapMapper;
import com.placy.placycore.core.processes.model.TaskInstanceModel;
import com.placy.placycore.core.processes.model.TaskInstanceStatusEnum;
import com.placy.placycore.core.processes.services.TasksService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

import javax.annotation.PostConstruct;

/**
 * @author ayeremeiev@netconomy.net
 */
@Component
@Scope("prototype")
public class TaskRunner implements Runnable {

    Logger LOG = LoggerFactory.getLogger(TaskRunner.class);

    private TaskInstanceModel taskInstanceModel = null;
    private ExecutableBean executableBean;
    private Map<String, Object> params;

    @Autowired
    private TasksService tasksService;

    @Autowired
    private TaskParamValueModelsToMapMapper taskParamValueModelsToMapMapper;

    public TaskRunner(TaskInstanceModel taskInstanceModel,
                      ExecutableBean executableBean) {
        this.taskInstanceModel = taskInstanceModel;
        this.executableBean = executableBean;
    }

    @PostConstruct
    public void init() {
        this.params = taskParamValueModelsToMapMapper.map(taskInstanceModel.getParamValues());
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void run() {
        assignTaskInstanceIfAware();

        taskInstanceModel.setStatus(TaskInstanceStatusEnum.PREPAIRING);
        taskInstanceModel.setStartDate(new Date());

        LOG.info("The task instance at {} with a task code {} has started",
             taskInstanceModel.getStartDate(),
             taskInstanceModel.getTask().getCode()
        );

        taskInstanceModel.setStatus(TaskInstanceStatusEnum.RUNNING);
        tasksService.save(taskInstanceModel);

        try {
            executableBean.execute(params);
            taskInstanceModel.setStatus(TaskInstanceStatusEnum.DONE);
        } catch (Exception ex) {
            LOG.error("Exception occurred during task {} execution.", taskInstanceModel.getCode(), ex);
            taskInstanceModel.setStatus(TaskInstanceStatusEnum.ERROR);
        }

        taskInstanceModel.setFinishDate(new Date());
        LOG.info("The task instance at {} with a task code {} has finished",
                 taskInstanceModel.getFinishDate(),
                 taskInstanceModel.getTask().getCode()
        );
        tasksService.save(taskInstanceModel);
    }

    private void assignTaskInstanceIfAware() {
        if(executableBean instanceof TaskInstanceModelAware) {
            if(taskInstanceModel == null) {
                throw new IllegalStateException("Task model was not specified");
            }

            TaskInstanceModelAware executableBean = (TaskInstanceModelAware) this.executableBean;
            executableBean.setTaskInstanceModel(taskInstanceModel);
        }
    }
}
