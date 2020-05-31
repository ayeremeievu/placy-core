package com.placy.placycore.core.processes.services;

import com.placy.placycore.core.processes.executable.ProcessRunner;
import com.placy.placycore.core.processes.model.ProcessInstanceModel;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

/**
 * @author ayeremeiev@netconomy.net
 */
@Component
public class ProcessRunnerService {

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private ObjectProvider<ProcessRunner> processRunnerObjectProvider;

    public void runProcess(ProcessInstanceModel processInstanceModel) {
        ProcessRunner processRunner = processRunnerObjectProvider.getObject(processInstanceModel.getCode());

        taskExecutor.execute(processRunner);
    }
}
