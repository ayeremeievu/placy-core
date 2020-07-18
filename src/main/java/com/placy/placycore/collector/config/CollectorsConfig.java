package com.placy.placycore.collector.config;

import com.placy.placycore.collector.tasks.executables.CollectDataExecutable;
import com.placy.placycore.core.processes.services.TasksService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * @author ayeremeiev@netconomy.net
 */
@Configuration
public class CollectorsConfig {
    @Bean("collectFullDataExecutable")
    public CollectDataExecutable collectDataExecutable(TasksService tasksService) {
        CollectDataExecutable collectDataExecutable = new CollectDataExecutable();

        collectDataExecutable.setTasksService(tasksService);
        collectDataExecutable.setDataCollectorsTasks(
            Arrays.asList("placy-data-collector", "yelp-data-collector")
        );

        return collectDataExecutable;
    }

    @Bean("collectOnlyPlacyDataExecutable")
    public CollectDataExecutable collectPlacyDataExecutable(TasksService tasksService) {
        CollectDataExecutable collectDataExecutable = new CollectDataExecutable();

        collectDataExecutable.setTasksService(tasksService);
        collectDataExecutable.setDataCollectorsTasks(
            Arrays.asList("placy-data-collector")
        );

        return collectDataExecutable;
    }

}
