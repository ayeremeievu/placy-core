package com.placy.placycore.reviewslearning.strategies;

import com.placy.placycore.core.model.UserModel;
import com.placy.placycore.reviewslearning.data.PredictionData;

import java.util.List;

public interface RecommendationStrategy {
    List<PredictionData> getPredictionsForUser(UserModel userModel);

    boolean canPredict(UserModel userModel);

    int getCoeffiecient();
}
