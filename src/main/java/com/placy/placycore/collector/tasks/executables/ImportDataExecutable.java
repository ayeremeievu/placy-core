package com.placy.placycore.collector.tasks.executables;

import com.placy.placycore.core.processes.executable.ExecutableBean;
import com.placy.placycore.core.processes.factories.RunTaskDataFactory;
import com.placy.placycore.core.processes.services.TasksService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

/**
 * @author ayeremeiev@netconomy.net
 */
public class ImportDataExecutable implements ExecutableBean {
    Logger LOG = LoggerFactory.getLogger(ImportDataExecutable.class);

    private final int NUMBER_OF_THREADS = 4;

    List<String> dataImporterTasks;

    TasksService tasksService;

    @PostConstruct
    public void init() {
        if (dataImporterTasks == null) {
            dataImporterTasks = new ArrayList<>();
        }
    }

    @Override
    public Object execute(Map<String, Object> params) {
        Executors.newFixedThreadPool(NUMBER_OF_THREADS);

        ExecutorCompletionService executorCompletionService =
            new ExecutorCompletionService(Executors.newFixedThreadPool(NUMBER_OF_THREADS));

        long countOfFutures = dataImporterTasks
            .stream()
            .map(RunTaskDataFactory::createRunTaskData)
            .map(tasksService::getTaskRunnable)
            .map(Executors::callable)
            .map(executorCompletionService::submit)
            .count();

        for (int i = 0; i < countOfFutures; i++) {
            try {
                executorCompletionService.take().get();
            } catch (InterruptedException ex) {
                LOG.error("InterruptedException occurred", ex);
                ex.printStackTrace();
            } catch (ExecutionException ex) {
                LOG.error("Exception occurred during collector execution", ex);
                ex.printStackTrace();
            }
        }

        return null;
    }

    public List<String> getDataImporterTasks() {
        return dataImporterTasks;
    }

    public void setDataImporterTasks(List<String> dataImporterTasks) {
        this.dataImporterTasks = dataImporterTasks;
    }

    public TasksService getTasksService() {
        return tasksService;
    }

    public void setTasksService(TasksService tasksService) {
        this.tasksService = tasksService;
    }
}
