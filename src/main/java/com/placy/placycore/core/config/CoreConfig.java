package com.placy.placycore.core.config;

import com.placy.placycore.core.processes.services.ProcessResourcesService;
import com.placy.placycore.core.processes.services.TaskResourcesService;
import com.placy.placycore.core.services.FileScannerService;
import com.placy.placycore.core.startuphooks.ApplicationReadyEventListener;
import com.placy.placycore.core.startuphooks.PostStartupHook;
import com.placy.placycore.core.startuphooks.hooks.DeleteOrphanProcesses;
import com.placy.placycore.core.startuphooks.hooks.DeleteOrphanTasks;
import com.placy.placycore.core.startuphooks.hooks.ResourceDefinitionImporterHook;
import com.placy.placycore.core.startuphooks.hooks.ResourceDefinitionsProcessorHook;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.List;

/**
 * @author a.yeremeiev@netconomy.net
 */
@Configuration
//@EnableTransactionManagement
public class CoreConfig {
    private final static String TASK_RESOURCES_BASE_PATH = "placycore/core/processes/definitions";
    private final static String PROCESS_RESOURCES_BASE_PATH = "placycore/core/processes/definitions";

    @Bean
    public ApplicationReadyEventListener applicationReadyEventListener() {
        ApplicationReadyEventListener applicationReadyEventListener = new ApplicationReadyEventListener();

        applicationReadyEventListener.setPostStartupHooks(postStartupHooks());

        return applicationReadyEventListener;
    }

    @Bean
    public List<PostStartupHook> postStartupHooks() {
        List<PostStartupHook> postStartupHooks = new ArrayList<>();

        postStartupHooks.add(resourceDefinitionImporterHook());
//        postStartupHooks.add(processDefinitionImporterHook());
//        postStartupHooks.add(deleteOrphanProcesses());
//        postStartupHooks.add(deleteOrphanTasks());
        postStartupHooks.add(resourceDefinitionsProcessorHook());

        return postStartupHooks;
    }

    @Bean
    public ResourceDefinitionImporterHook resourceDefinitionImporterHook() {
        return new ResourceDefinitionImporterHook();
    }

    @Bean
    public DeleteOrphanProcesses deleteOrphanProcesses() {
        return new DeleteOrphanProcesses();
    }

    @Bean
    public DeleteOrphanTasks deleteOrphanTasks() {
        return new DeleteOrphanTasks();
    }

    @Bean
    public FileScannerService fileScannerService() {
        return new FileScannerService();
    }

    @Bean
    public ResourceDefinitionsProcessorHook resourceDefinitionsProcessorHook() {
        return new ResourceDefinitionsProcessorHook();
    }

    @Bean
    public ProcessResourcesService processResourcesService() {
        return new ProcessResourcesService();
    }

    @Bean
    public TaskResourcesService taskResourcesService() {
        return new TaskResourcesService();
    }

//    private List<String> processesResourcesPaths() {
//        return Arrays.asList(
//            "/placycore/core/processes/definitions/three-step-hello-world.process.json",
//
//            "/placycore/core/processes/definitions/collectors/processes/full-update.process.json"
//        );
//    }
//
//    private List<String> tasksResourcesPaths() {
//        return Arrays.asList(
//            "/placycore/core/processes/definitions/tasks/first-step-hello-world.task.json",
//            "/placycore/core/processes/definitions/tasks/log-string.task.json",
//
//            "/placycore/core/processes/definitions/collectors/tasks/import-full-data.task.json",
//            "/placycore/core/processes/definitions/collectors/tasks/import-placy-data.task.json",
//            "/placycore/core/processes/definitions/collectors/tasks/import-yelp-data.task.json"
//        );
//    }

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
