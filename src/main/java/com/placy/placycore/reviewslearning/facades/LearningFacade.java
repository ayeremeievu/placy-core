package com.placy.placycore.reviewslearning.facades;

import com.placy.placycore.core.model.CityModel;
import com.placy.placycore.core.services.CityService;
import com.placy.placycore.reviewslearning.exceptions.LearningProcessException;
import com.placy.placycore.reviewslearning.model.LearningProcessModel;
import com.placy.placycore.reviewslearning.model.LearningProcessStatusEnum;
import com.placy.placycore.reviewslearning.services.AlsModelService;
import com.placy.placycore.reviewslearning.services.LearningProcessesService;
import com.placy.placycore.sparkcore.services.SparkJobService;
import com.placy.placycore.sparklearner.services.SparkLearnerJobService;
import com.placy.placycore.sparklearner.services.SparkModelsRegistryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.spark.ml.recommendation.ALSModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;

@Component
public class LearningFacade {

    private static final Logger LOG = LoggerFactory.getLogger(LearningFacade.class);

    @Autowired
    private CityService cityService;

    @Autowired
    private LearningProcessesService learningProcessesService;

    @Autowired
    private SparkLearnerJobService sparkLearnerJobService;

    @Autowired
    private SparkModelsRegistryService sparkModelsRegistryService;

    @Autowired
    private AlsModelService alsModelService;

    public void processCities(LearningProcessModel learningProcessModel, List<CityModel> cities) {
        if(CollectionUtils.isEmpty(cities)) {
            return;
        }

        cities.forEach(city -> trainByCity(learningProcessModel, city));
    }

    public void trainByCity(LearningProcessModel learningProcessModel) {
        Assert.notNull(learningProcessModel, "learningProcessModel must be not null");

        CityModel cityModel = learningProcessModel.getCityModel();
        Assert.notNull(cityModel,
                "learningProcessModel.getCityModel() must be not null");

        trainByCity(learningProcessModel, cityModel);
    }

    public void trainByCity(LearningProcessModel learningProcessModel, CityModel city) {
        Integer id = city.getId();

        Process sparkProcess = sparkLearnerJobService.runJob(id);

        try {
            sparkProcess.waitFor();

            learningProcessModel.setStatus(LearningProcessStatusEnum.LEARNING_FINISHED);
        } catch (InterruptedException e) {
            learningProcessModel.setStatus(LearningProcessStatusEnum.ABORTED);
            learningProcessesService.save(learningProcessModel);

            LOG.warn("The training process thread with pk '{}' was interrupted", learningProcessModel.getPk());
        }
    }

    public void loadModel(LearningProcessModel learningProcessModel) {
        CityModel cityModel = learningProcessModel.getCityModel();

        sparkLearnerJobService.loadModel(cityModel.getId());

        learningProcessModel.setStatus(LearningProcessStatusEnum.LOADING_FINISHED);
    }

    public void processModel(LearningProcessModel learningProcessModel) {
        CityModel cityModel = learningProcessModel.getCityModel();

        Integer cityId = cityModel.getId();

        if(sparkModelsRegistryService.containsModelByCityId(cityId)) {
            ALSModel alsModel = sparkModelsRegistryService.getModelByCityId(cityId);

            processModel(cityModel, alsModel);
        } else {
            throw new LearningProcessException(
                    String.format("No model found in the registry for the city '%s'. " +
                            "At first you have to load a model.", cityId)
            );
        }
    }

    private void processModel(CityModel cityModel, ALSModel alsModel) {
        alsModelService.processAlsModel(cityModel, alsModel);
    }
}
