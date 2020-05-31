package com.placy.placycore.core.config;

import com.placy.placycore.core.processes.services.ProcessResourcesService;
import com.placy.placycore.core.processes.services.TaskResourcesService;
import com.placy.placycore.core.startuphooks.ApplicationReadyEventListener;
import com.placy.placycore.core.startuphooks.PostStartupHook;
import com.placy.placycore.core.startuphooks.hooks.ProcessDefinitionImporterHook;
import com.placy.placycore.core.startuphooks.hooks.ProcessDefinitionsProcessorHook;
import com.placy.placycore.core.startuphooks.hooks.TaskDefinitionImporterHook;
import com.placy.placycore.core.startuphooks.hooks.TaskDefinitionsProcessorHook;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Configuration
public class CoreConfig {
    @Bean
    public ApplicationReadyEventListener applicationReadyEventListener() {
        ApplicationReadyEventListener applicationReadyEventListener = new ApplicationReadyEventListener();

        applicationReadyEventListener.setPostStartupHooks(postStartupHooks());

        return applicationReadyEventListener;
    }

    @Bean
    public List<PostStartupHook> postStartupHooks() {
        List<PostStartupHook> postStartupHooks = new ArrayList<>();

        postStartupHooks.add(taskDefinitionImporterHook());
        postStartupHooks.add(taskDefinitionsProcessorHook());
        postStartupHooks.add(processDefinitionImporterHook());
        postStartupHooks.add(processDefinitionsProcessorHook());

        return postStartupHooks;
    }

    @Bean
    public TaskDefinitionImporterHook taskDefinitionImporterHook() {
        TaskDefinitionImporterHook taskDefinitionImporterHook = new TaskDefinitionImporterHook();

        taskDefinitionImporterHook.setTasksResourcesPaths(tasksResourcesPaths());
        taskDefinitionImporterHook.setTasksResourcesService(taskResourcesService());

        return taskDefinitionImporterHook;
    }

    @Bean
    public TaskDefinitionsProcessorHook taskDefinitionsProcessorHook() {
        return new TaskDefinitionsProcessorHook();
    }

    @Bean
    public ProcessDefinitionImporterHook processDefinitionImporterHook() {
        ProcessDefinitionImporterHook processDefinitionImporterHook = new ProcessDefinitionImporterHook();

        processDefinitionImporterHook.setProcessesResourcesPaths(processesResourcesPaths());
        processDefinitionImporterHook.setProcessResourcesService(processResourcesService());

        return processDefinitionImporterHook;
    }

    @Bean
    public ProcessDefinitionsProcessorHook processDefinitionsProcessorHook() {
        return new ProcessDefinitionsProcessorHook();
    }

    @Bean
    public ProcessResourcesService processResourcesService() {
        return new ProcessResourcesService();
    }

    @Bean
    public TaskResourcesService taskResourcesService() {
        return new TaskResourcesService();
    }

    private List<String> processesResourcesPaths() {
        return Arrays.asList(
            "/placycore/core/processes/definitions/three-step-hello-world-process.json"
        );
    }

    private List<String> tasksResourcesPaths() {
        return Arrays.asList(
            "/placycore/core/processes/definitions/tasks/first-step-hello-world-task.json",
            "/placycore/core/processes/definitions/tasks/log-string-task.json"
        );
    }

    @Bean
    public TaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(4);
        executor.setThreadNamePrefix("core_task_executor_thread");
        executor.initialize();

        return executor;
    }
}
