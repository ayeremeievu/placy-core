package com.placy.placycore.reviewslearning.facades;

import com.placy.placycore.core.model.CityModel;
import com.placy.placycore.core.services.CityService;
import com.placy.placycore.reviewslearning.model.LearningProcessModel;
import com.placy.placycore.reviewslearning.services.LearningProcessesService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Component
public class LearningFacade {

    @Autowired
    private CityService cityService;

    @Autowired
    private LearningProcessesService learningProcessesService;

    public void processCities(LearningProcessModel learningProcessModel, List<CityModel> cities) {
        if(CollectionUtils.isEmpty(cities)) {
            return;
        }

        cities.forEach(this::startCityTraining);
    }

    public void startCityTraining(LearningProcessModel learningProcessModel, Integer pk) {
        Assert.notNull(learningProcessModel, "learningProcessModel must be not null");

        CityModel cityModel = learningProcessModel.getCityModel();
        Assert.notNull(cityModel,
                "learningProcessModel.getCityModel() must be not null");

        learningProcessModel.setCityModel(cityModel);


    }

    private CityModel getCityModel(Integer pk) {
        Optional<CityModel> cityModelOptional = cityService.getByPkOptional(pk);

        if(!cityModelOptional.isPresent()) {
            throw new IllegalArgumentException("No city with name found");
        }

        return cityModelOptional.get();
    }

    public void startCityTraining(CityModel city) {

    }
}
