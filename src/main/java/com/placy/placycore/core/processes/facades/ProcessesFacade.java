package com.placy.placycore.core.processes.facades;

import com.placy.placycore.core.processes.data.RunProcessData;
import com.placy.placycore.core.processes.model.ProcessInstanceModel;
import com.placy.placycore.core.processes.services.ProcessesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProcessesFacade {

    @Autowired
    private ProcessesService processesService;

    public ProcessInstanceModel startProcess(RunProcessData runProcessData) {
        ProcessInstanceModel processInstanceModel = processesService.initializeProcess(runProcessData);

        return processesService.runProcess(processInstanceModel);
    }
}
