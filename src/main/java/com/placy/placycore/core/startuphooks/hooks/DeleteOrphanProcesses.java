package com.placy.placycore.core.startuphooks.hooks;

import com.placy.placycore.core.processes.model.ProcessModel;
import com.placy.placycore.core.processes.services.ProcessResourcesService;
import com.placy.placycore.core.processes.services.ProcessesService;
import com.placy.placycore.core.startuphooks.PostStartupHook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ayeremeiev@netconomy.net
 */
public class DeleteOrphanProcesses implements PostStartupHook {

    private final static Logger LOG = LoggerFactory.getLogger(DeleteOrphanProcesses.class);

    @Autowired
    private ProcessResourcesService processResourcesService;

    @Autowired
    private ProcessesService processesService;

    @Override
    public Object run(ApplicationContext applicationContext) {
        LOG.info("DeleteOrphanProcesses started");

        cleanOrphanProcesses();

        LOG.info("DeleteOrphanProcesses finished");
        return null;
    }

    private void cleanOrphanProcesses() {
        List<ProcessModel> processes = processesService.getAllLastProcesses();

        List<ProcessModel> orphanProcesses = processes.stream()
                                                      .filter(this::doesntExistsResource)
                                                      .collect(Collectors.toList());

        try {
            processesService.removeAll(orphanProcesses);
        } catch (RuntimeException ex) {
            throw new IllegalStateException(
                "Exception occurred during removal of orphan tasks. Check the dependencies between processes steps and tasks.",
                ex);
        }

        orphanProcesses.forEach(orphanProcess ->
                                    LOG.info("Removed process with code {} and pk {}",
                                             orphanProcess.getCode(),
                                             orphanProcess.getPk()
                                    ));
    }

    private boolean doesntExistsResource(ProcessModel processModel) {
        return processResourcesService.getResourceByProcess(processModel).isEmpty();
    }
}
