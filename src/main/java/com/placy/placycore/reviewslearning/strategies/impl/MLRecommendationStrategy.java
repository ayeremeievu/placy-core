package com.placy.placycore.reviewslearning.strategies.impl;

import com.placy.placycore.core.model.UserModel;
import com.placy.placycore.reviewslearning.data.PredictionData;
import com.placy.placycore.reviewslearning.services.TrainedRecommendationsInMemoryStorageService;
import com.placy.placycore.reviewslearning.strategies.RecommendationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MLRecommendationStrategy implements RecommendationStrategy {
    @Autowired
    private TrainedRecommendationsInMemoryStorageService storageService;

    @Override
    public List<PredictionData> getPredictionsForUser(UserModel userModel) {
        return storageService.getPredictions(userModel);
    }

    @Override
    public boolean canPredict(UserModel userModel) {
        return storageService.hasPredictions(userModel);
    }

    @Override
    public int getCoeffiecient() {
        return 10;
    }
}
