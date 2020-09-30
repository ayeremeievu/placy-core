package com.placy.placycore.reviewslearning.facades;

import com.placy.placycore.core.model.CityModel;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LearningFacade {

    public void processCities(List<CityModel> cities) {
        if(CollectionUtils.isEmpty(cities)) {
            return;
        }

        cities.forEach(this::processCity);
    }

    public void processCity(CityModel city) {

    }
}
