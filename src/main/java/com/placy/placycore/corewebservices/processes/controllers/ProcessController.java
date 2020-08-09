package com.placy.placycore.corewebservices.processes.controllers;

import com.placy.placycore.core.processes.data.ProcessInstanceData;
import com.placy.placycore.core.processes.data.RunProcessData;
import com.placy.placycore.core.processes.model.ProcessModel;
import com.placy.placycore.core.processes.services.ProcessesService;
import com.placy.placycore.corewebservices.constants.CorewebservicesRouteConstants;
import com.placy.placycore.corewebservices.processes.dto.ProcessDto;
import com.placy.placycore.corewebservices.processes.dto.ProcessInstanceDto;
import com.placy.placycore.corewebservices.processes.dto.RunProcessDto;
import com.placy.placycore.corewebservices.processes.mappers.ProcessInstanceMapper;
import com.placy.placycore.corewebservices.processes.mappers.ProcessModelToDtoMapper;
import com.placy.placycore.corewebservices.processes.mappers.RunProcessMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

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

    @Autowired
    private ProcessInstanceMapper processInstanceMapper;

    @RequestMapping(path = "/processes")
    public List<ProcessDto> getProcesses() {
        List<ProcessModel> processes = processesService.getAllLastProcesses();

        return processModelToDtoMapper.mapAll(processes);
    }

    @RequestMapping(path = "/processes/{code}", method = RequestMethod.GET)
    public ProcessDto getProcessByCode(@PathVariable(name = "code") String code) {
        Optional<ProcessModel> processByCodeOptional = processesService.getLastProcessByCodeOptional(code);

        return processModelToDtoMapper.map(
            processByCodeOptional.orElseThrow(() ->
                                                  new ResponseStatusException(NOT_FOUND, "Process not found for code : " + code)
            )
        );
    }

    @RequestMapping(path = "/process-instances", method = RequestMethod.POST)
    public void createProcessInstance(@RequestBody RunProcessDto runProcessDto) {
        RunProcessData runProcessData = runProcessMapper.map(runProcessDto);

        processesService.startProcess(runProcessData);
    }

    @RequestMapping(path = "/process-instances", method = RequestMethod.GET)
    public List<ProcessInstanceDto> getAllProcessInstances() {
        List<ProcessInstanceData> allProcessInstances = processesService.getAllProcessInstances();

        return processInstanceMapper.processInstanceDataToDtoList(allProcessInstances);
    }
}
