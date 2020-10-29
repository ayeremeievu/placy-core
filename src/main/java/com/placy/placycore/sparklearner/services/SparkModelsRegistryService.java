package com.placy.placycore.sparklearner.services;

import org.apache.spark.ml.recommendation.ALSModel;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SparkModelsRegistryService {
    private final Map<Integer, ALSModel> citiesToModelsMap;

    public SparkModelsRegistryService() {
        citiesToModelsMap = new ConcurrentHashMap<>();
    }

    public void putModel(int cityId, ALSModel model) {
        citiesToModelsMap.put(cityId, model);
    }

    public void removeModel(int cityId) {
        citiesToModelsMap.remove(cityId);
    }

    public ALSModel getModelByCityId(int cityId) {
        return citiesToModelsMap.get(cityId);
    }

    public boolean containsModelByCityId(int cityId) {
        return citiesToModelsMap.containsKey(cityId);
    }
}
