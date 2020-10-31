package com.placy.placycore.sparkcore.services;

import com.placy.placycore.core.config.PathsConfig;
import com.placy.placycore.sparkcore.configs.SparkConfig;
import com.placy.placycore.sparkcore.data.SparkJobCreationData;
import com.placy.placycore.sparkcore.exceptions.SparkLaunchingException;
import org.apache.hadoop.fs.Path;
import org.apache.spark.launcher.SparkLauncher;
import org.apache.spark.ml.param.IntParam;
import org.apache.spark.ml.param.Param;
import org.apache.spark.ml.param.ParamValidators$;
import org.apache.spark.ml.param.Params$class;
import org.apache.spark.ml.recommendation.ALS;
import org.apache.spark.ml.recommendation.ALSModel;
import org.apache.spark.ml.util.DefaultParamsReader;
import org.apache.spark.ml.util.DefaultParamsReader$;
import org.apache.spark.ml.util.MLReader;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.json4s.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import scala.reflect.ManifestFactory;
import scala.runtime.BoxesRunTime;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class SparkJobService {

    private static final Logger LOG = LoggerFactory.getLogger(SparkJobService.class);

    @Autowired
    private SparkConfig sparkConfig;

    @Autowired
    private PathsConfig pathsConfig;

    public Process startSparkJob(SparkJobCreationData sparkJobCreationData) {
        Assert.notNull(sparkJobCreationData, "jobCreationData has to be not null");

        SparkLauncher sparkLauncher = initSparkLauncher(sparkJobCreationData);

        enhanceWithConfigs(sparkLauncher, sparkJobCreationData);
        enhanceWithJars(sparkLauncher, sparkJobCreationData);

        sparkLauncher.redirectToLog(SparkJobService.class.getName());

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
                .setDeployMode(sparkJobCreationData.getDeployMode())
                .setJavaHome(pathsConfig.getJavaHomePath());
    }

    private void enhanceWithJars(SparkLauncher sparkLauncher, SparkJobCreationData sparkJobCreationData) {
        List<String> jars = sparkJobCreationData.getJars();

        if(jars != null) {
            jars.forEach(sparkLauncher::addJar);
        }
    }

    public ALSModel loadModel(String modelPath) {
        SparkSession spark = SparkSession
                .builder()
                .appName("Placy reviews learning task")
                .config("spark.sql.crossJoin.enabled", true)
                .master("local[*]")
                .getOrCreate();

        ALSModel model = readModel(modelPath, spark);

        return model;
    }

    private ALSModel readModel(String modelPath, SparkSession spark) {
        DefaultParamsReader.Metadata metadata = DefaultParamsReader.loadMetadata(modelPath, spark.sparkContext(), ALSModel.class.getName());

        String userPath = (new Path(modelPath, "userFactors")).toString();
        Dataset userFactors = spark.read().format("parquet").load(userPath);

        String itemPath = (new Path(modelPath, "itemFactors")).toString();
        Dataset itemFactors = spark.read().format("parquet").load(itemPath);

        int rank = getRank(metadata);

        ALSModel model = new ALSModel(metadata.uid(), rank, userFactors, itemFactors);

        IntParam intParam = new IntParam(model.uid(), "blockSize", "doc", ParamValidators$.MODULE$.alwaysTrue());
        model.setDefault(intParam, 4096);

        setModelDefaultParam(model, "userCol", "r_user_pk");
        setModelDefaultParam(model, "itemCol", "r_place_pk");
        setModelDefaultParam(model, "coldStartStrategy", "drop");
        setModelDefaultParam(model, "predictionCol", "prediction");

        return model;
    }

    private <T> void setModelDefaultParam(ALSModel alsModel, String paramName, T value) {
        Param<T> param = new Param<>(alsModel, paramName, "doc");

        alsModel.setDefault(param, value);
    }

    private int getRank(DefaultParamsReader.Metadata metadata) {
        MonadicJValue monadicJValue = new MonadicJValue(metadata.metadata());
        JsonAST.JValue rankJv = monadicJValue.$bslash("rank");

        ExtractableJsonAstNode extractableJsonAstNode = new ExtractableJsonAstNode(rankJv);
        return BoxesRunTime.unboxToInt(extractableJsonAstNode.extract(DefaultFormats$.MODULE$, ManifestFactory.Int()));
    }
}
