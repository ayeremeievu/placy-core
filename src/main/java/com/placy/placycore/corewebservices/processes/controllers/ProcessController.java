package com.placy.placycore.corewebservices.processes.controllers;

import com.placy.placycore.core.processes.model.ProcessModel;
import com.placy.placycore.core.processes.services.ProcessesService;
import com.placy.placycore.corewebservices.constants.CorewebservicesRouteConstants;
import com.placy.placycore.corewebservices.processes.dto.ProcessDto;
import com.placy.placycore.corewebservices.processes.mappers.ProcessModelToDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @RequestMapping(path = "/processes")
    public List<ProcessDto> getProcesses() {
        List<ProcessModel> processes = processesService.getProcesses();

        return processModelToDtoMapper.mapAll(processes);
    }
}
