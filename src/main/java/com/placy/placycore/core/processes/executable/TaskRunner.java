package com.placy.placycore.core.processes.executable;

import com.placy.placycore.core.processes.model.TaskInstanceModel;
import com.placy.placycore.core.processes.model.TaskInstanceStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;

/**
 * @author ayeremeiev@netconomy.net
 */
public class TaskRunner implements Runnable {
    Logger LOG = LoggerFactory.getLogger(TaskRunner.class);

    private TaskInstanceModel taskInstanceModel = null;
    private final ExecutableBean executableBean;
    private final Map<String, Object> params;

    public TaskRunner(ExecutableBean executableBean, Map<String, Object> params) {
        this.executableBean = executableBean;
        this.params = params;
    }

    public TaskRunner(
        TaskInstanceModel taskInstanceModel,
        ExecutableBean executableBean,
        Map<String, Object> params
    ) {
        this.taskInstanceModel = taskInstanceModel;
        this.executableBean = executableBean;
        this.params = params;
    }

    @Override
    public void run() {
        assignTaskInstanceIfAware();

        taskInstanceModel.setStatus(TaskInstanceStatusEnum.PREPAIRING);
        taskInstanceModel.setStartDate(new Date());

        LOG.info("The task instance with pk {} with a task code {} has started",
             taskInstanceModel.getPk(),
             taskInstanceModel.getTask().getCode()
        );
        executableBean.execute(params);
        LOG.info("The task instance with pk {} with a task code {} has finished",
                 taskInstanceModel.getPk(),
                 taskInstanceModel.getTask().getCode()
        );
        taskInstanceModel.setStatus(TaskInstanceStatusEnum.DONE);
        taskInstanceModel.setFinishDate(new Date());
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
