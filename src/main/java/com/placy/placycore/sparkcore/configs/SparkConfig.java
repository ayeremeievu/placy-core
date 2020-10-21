package com.placy.placycore.sparkcore.configs;

import com.placy.placycore.core.config.PathsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SparkConfig {

    @Autowired
    private PathsConfig pathsConfig;

    @Value("${SPARK_HOME}")
    private String sparkHomePath;

    @PostConstruct
    public void init() {
        if(sparkHomePath == null) {
            throw new IllegalStateException("No SPARK_HOME os environment variable present in the system");
        }
    }

    public String getSparkHomePath() {
        return sparkHomePath;
    }

    public String getSparkPostgresJarPath() {
        return pathsConfig.getPlacyDataFolderPath() + "\\spark\\jars\\postgresql-42.2.18.jar";
    }

    public String getSparkModelPath() {
        return pathsConfig.getPlacyDataFolderPath() + "\\spark\\model";
    }

    public String getSparkLocalDirPath() {
        return pathsConfig.getPlacyDataFolderPath() + "\\spark\\model";
    }

    public String getSparkJobJarPath() {
        return pathsConfig.getPlacyDataFolderPath() + "\\spark\\jars\\recommender-learner-job-0.1.jar";
    }
}
