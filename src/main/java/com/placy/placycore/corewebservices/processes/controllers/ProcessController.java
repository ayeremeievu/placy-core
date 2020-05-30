package com.placy.placycore.corewebservices.processes.controllers;

import com.placy.placycore.core.processes.data.RunProcessData;
import com.placy.placycore.core.processes.model.ProcessModel;
import com.placy.placycore.core.processes.services.ProcessesService;
import com.placy.placycore.corewebservices.constants.CorewebservicesRouteConstants;
import com.placy.placycore.corewebservices.processes.dto.ProcessDto;
import com.placy.placycore.corewebservices.processes.dto.RunProcessDto;
import com.placy.placycore.corewebservices.processes.mappers.ProcessModelToDtoMapper;
import com.placy.placycore.corewebservices.processes.mappers.RunProcessMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author a.yeremeiev@netconomy.net
 */
@RestController
@RequestMapping(path = CorewebservicesRouteConstants.URI_PREFIX)
public class ProcessController {
    @Autowired
    private ProcessesService processesService;

    @Autowired
    private ProcessModelToDtoMapper processModelToDtoMapper;

    @Autowired
    private RunProcessMapper runProcessMapper;

    @RequestMapping(path = "/processes")
    public List<ProcessDto> getProcesses() {
        List<ProcessModel> processes = processesService.getProcesses();

        return processModelToDtoMapper.mapAll(processes);
    }

    @RequestMapping(path = "/process-instances", method = RequestMethod.POST)
    public void createProcessInstance(@RequestBody RunProcessDto runProcessDto) {
        RunProcessData runProcessData = runProcessMapper.map(runProcessDto);

        processesService.startProcess(runProcessData);
    }
}
