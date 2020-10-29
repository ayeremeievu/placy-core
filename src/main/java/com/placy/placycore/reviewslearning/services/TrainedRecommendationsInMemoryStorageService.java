package com.placy.placycore.reviewslearning.services;

import com.placy.placycore.core.model.UserModel;
import com.placy.placycore.reviewslearning.comparators.PredictionRateComparator;
import com.placy.placycore.sparklearner.data.PredictionData;
import com.placy.placycore.sparklearner.data.PredictionDataList;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Component
public class TrainedRecommendationsInMemoryStorageService {
    private ConcurrentMap<UserModel, PredictionDataList> predictionsMap;

    public TrainedRecommendationsInMemoryStorageService() {
        predictionsMap = new ConcurrentHashMap<>();
    }


    public void putPredictions(UserModel userModel, PredictionDataList predictionDataList) {
        predictionsMap.put(userModel, predictionDataList);
    }

    public List<PredictionData> getPredictions(UserModel userModel) {
        PredictionDataList predictionDataList = predictionsMap.getOrDefault(userModel, PredictionDataList.empty());

        return predictionDataList.getPredictionDataList()
                .stream()
                .sorted(new PredictionRateComparator())
                .collect(Collectors.toList());
    }

    public void removePredictions(UserModel userModel) {
        predictionsMap.remove(userModel);
    }
}
