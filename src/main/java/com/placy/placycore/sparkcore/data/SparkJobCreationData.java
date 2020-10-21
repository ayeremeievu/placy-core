package com.placy.placycore.sparkcore.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SparkJobCreationData {

    private String jobJarPath;

    private String mainClass;

    private String master;

    private String deployMode;

    private Map<String, String> configs = new HashMap<>();

    private List<String> jars = new ArrayList<>();

    public static SparkJobCreationData of() {
        return new SparkJobCreationData();
    }

    public String getJobJarPath() {
        return jobJarPath;
    }

    public SparkJobCreationData setJobJarPath(String jobJarPath) {
        this.jobJarPath = jobJarPath;
        return this;
    }

    public String getMainClass() {
        return mainClass;
    }

    public SparkJobCreationData setMainClass(String mainClass) {
        this.mainClass = mainClass;
        return this;
    }

    public String getMaster() {
        return master;
    }

    public SparkJobCreationData setMaster(String master) {
        this.master = master;
        return this;
    }

    public String getDeployMode() {
        return deployMode;
    }

    public SparkJobCreationData setDeployMode(String deployMode) {
        this.deployMode = deployMode;
        return this;
    }

    public Map<String, String> getConfigs() {
        return configs;
    }

    public SparkJobCreationData addConfig(String key, String value) {
        configs.put(key, value);
        return this;
    }

    public SparkJobCreationData removeConfig(String key) {
        configs.remove(key);
        return this;
    }

    public List<String> getJars() {
        return jars;
    }

    public SparkJobCreationData addJarPath(String jarPath) {
        jars.add(jarPath);
        return this;
    }

    public SparkJobCreationData removeJarPath(String jarPath) {
        jars.remove(jarPath);
        return this;
    }
}
