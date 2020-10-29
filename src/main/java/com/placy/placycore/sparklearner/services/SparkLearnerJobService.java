package com.placy.placycore.sparklearner.services;

import com.placy.placycore.sparkcore.configs.SparkConfig;
import com.placy.placycore.sparkcore.data.SparkJobCreationData;
import com.placy.placycore.sparkcore.services.SparkJobService;
import org.apache.spark.launcher.SparkLauncher;
import org.apache.spark.ml.recommendation.ALSModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SparkLearnerJobService {

    private final static String MAIN_CLASS_NAME = "com.ayeremeiev.Application";
    private final static String EXECUTOR_MEMORY = "8G";

    private final static String MASTER = "local[*]";

    private final static String CONF_CITY_PK_NAME = "spark.recommender-learner-job.cityPk";
    private final static String CONF_MODEL_PATH_NAME = "spark.recommender-learner-job.modelPath";
    private final static String CONF_HAS_TO_EVALUATE_NAME = "spark.recommender-learner-job.hasToEvaluate";
    private final static String CONF_LOCAL_DIR_NAME = "spark.local.dir";
    private final static boolean CONF_HAS_TO_EVALUATE_DEFAULT_VALUE = false;

    private final static String DEPLOY_MODE = "client";

    private final static String MODEL_PATH_TEMPLATE = "%s-%s";

    @Autowired
    private SparkJobService sparkJobService;

    @Autowired
    private SparkConfig sparkConfig;

    @Autowired
    private SparkModelsRegistryService sparkModelsRegistryService;

    public Process runJob(int cityPk) {
        SparkJobCreationData sparkJobCreationData = SparkJobCreationData.of()
                .setMainClass(MAIN_CLASS_NAME)
                .addConfig(SparkLauncher.EXECUTOR_MEMORY, EXECUTOR_MEMORY)
                .addJarPath(sparkConfig.getSparkPostgresJarPath())
//                TODO set spark home
                .setMaster(MASTER)
                .addConfig(CONF_CITY_PK_NAME, String.valueOf(cityPk))
                .addConfig(CONF_MODEL_PATH_NAME, getModelPath(cityPk))
                .addConfig(CONF_HAS_TO_EVALUATE_NAME, String.valueOf(CONF_HAS_TO_EVALUATE_DEFAULT_VALUE))
                .addConfig(CONF_LOCAL_DIR_NAME, sparkConfig.getSparkLocalDirPath())
                .setDeployMode(DEPLOY_MODE)
                .setJobJarPath(sparkConfig.getSparkJobJarPath());

        return sparkJobService.startSparkJob(sparkJobCreationData);
    }

    private String getModelPath(int cityPk) {
        return String.format(MODEL_PATH_TEMPLATE, sparkConfig.getSparkModelPath(), cityPk);
    }

    public void loadModel(int cityPk) {
        String modelPath = getModelPath(cityPk);

        ALSModel alsModel = sparkJobService.loadModel(modelPath);

        sparkModelsRegistryService.putModel(cityPk, alsModel);
    }
}
