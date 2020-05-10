package com.placy.placycore.core.processes.services;

import com.placy.placycore.core.processes.model.ProcessModel;
import com.placy.placycore.core.processes.repository.ProcessesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Component
public class ProcessesService {
    @Autowired
    private ProcessesRepository processesRepository;

    public void save(ProcessModel processModel) {
        processesRepository.save(processModel);
    }

    public Optional<ProcessModel> getProcessByCodeOptional(String code) {
        return processesRepository.getFirstByCode(code);
    }

    public List<ProcessModel> getProcesses() {
        return processesRepository.findAll();
    }
}
