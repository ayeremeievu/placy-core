package com.placy.placycore.sparkcore.services;

import com.placy.placycore.sparkcore.configs.SparkConfig;
import com.placy.placycore.sparkcore.data.SparkJobCreationData;
import com.placy.placycore.sparkcore.exceptions.SparkLaunchingException;
import org.apache.spark.launcher.SparkLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class SparkJobService {

    @Autowired
    private SparkConfig sparkConfig;

    public Process startSparkJob(SparkJobCreationData sparkJobCreationData) {
        Assert.notNull(sparkJobCreationData, "jobCreationData has to be not null");

        SparkLauncher sparkLauncher = initSparkLauncher(sparkJobCreationData);

        enhanceWithConfigs(sparkLauncher, sparkJobCreationData);
        enhanceWithJars(sparkLauncher, sparkJobCreationData);

        Process launchedProcess = null;
        try {
            launchedProcess = sparkLauncher.launch();
        } catch (IOException e) {
            throw new SparkLaunchingException("Exception occurred during launching of spark process " +
                    "with data : " + sparkJobCreationData);
        }

        return launchedProcess;
    }

    private void enhanceWithConfigs(SparkLauncher sparkLauncher, SparkJobCreationData sparkJobCreationData) {
        Map<String, String> configs = sparkJobCreationData.getConfigs();

        if(configs != null) {
            configs.forEach(sparkLauncher::setConf);
        }
    }

    private SparkLauncher initSparkLauncher(SparkJobCreationData sparkJobCreationData) {
        SparkLauncher sparkLauncher = new SparkLauncher();

        return sparkLauncher
                .setSparkHome(sparkConfig.getSparkHomePath())
                .setAppResource(sparkJobCreationData.getJobJarPath())
                .setMainClass(sparkJobCreationData.getMainClass())
                .setDeployMode(sparkJobCreationData.getDeployMode());
    }

    private void enhanceWithJars(SparkLauncher sparkLauncher, SparkJobCreationData sparkJobCreationData) {
        List<String> jars = sparkJobCreationData.getJars();

        if(jars != null) {
            jars.forEach(sparkLauncher::addJar);
        }
    }
}
