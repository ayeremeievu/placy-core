package com.placy.placycore.core.startuphooks.hooks;

import com.placy.placycore.core.processes.model.TaskModel;
import com.placy.placycore.core.processes.services.TaskResourcesService;
import com.placy.placycore.core.processes.services.TasksService;
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
public class DeleteOrphanTasks implements PostStartupHook {

    private final static Logger LOG = LoggerFactory.getLogger(DeleteOrphanTasks.class);

    @Autowired
    private TaskResourcesService taskResourcesService;

    @Autowired
    private TasksService tasksService;

    @Override
    public Object run(ApplicationContext applicationContext) {
        LOG.info("DeleteOrphanTasks started");

        cleanOrphanTasks();

        LOG.info("DeleteOrphanTasks finished");

        return null;
    }

    private void cleanOrphanTasks() {
        List<TaskModel> tasks = tasksService.getAllTasks();

        List<TaskModel> orphanTasks = tasks.stream()
                                           .filter(this::doesntExistsResource)
                                           .collect(Collectors.toList());

        try {
            tasksService.removeAll(orphanTasks);
        } catch (RuntimeException ex) {
            throw new IllegalStateException(
                "Exception occurred during removal of orphan tasks. Check the dependencies between processes steps and tasks.",
                ex);
        }

        orphanTasks.forEach(orphanTask -> LOG.info("Removed task with code {} and pk {}", orphanTask.getCode(), orphanTask.getPk()));
    }

    private boolean doesntExistsResource(TaskModel processModel) {
        return taskResourcesService.getResourceByTask(processModel).isEmpty();
    }
}
