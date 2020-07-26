package com.placy.placycore.collector.config;

import com.placy.placycore.collector.tasks.executables.ImportDataExecutable;
import com.placy.placycore.core.processes.services.TasksService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * @author ayeremeiev@netconomy.net
 */
@Configuration
public class CollectorsConfig {
    @Bean("importFullDataExecutable")
    public ImportDataExecutable importDataExecutable(TasksService tasksService) {
        ImportDataExecutable importDataExecutable = new ImportDataExecutable();

        importDataExecutable.setTasksService(tasksService);
        importDataExecutable.setDataImporterTasks(
            Arrays.asList("placy-data-collector", "yelp-data-collector")
        );

        return importDataExecutable;
    }

//    @Bean("importOnlyPlacyDataExecutable")
//    public ImportDataExecutable importPlacyDataExecutable(TasksService tasksService) {
//        ImportDataExecutable importDataExecutable = new ImportDataExecutable();
//
//        importDataExecutable.setTasksService(tasksService);
//        importDataExecutable.setDataImporterTasks(
//            Arrays.asList("placy-data-collector")
//        );
//
//        return importDataExecutable;
//    }

}
