package com.placy.placycore.reviewslearning.strategies.impl;

import com.placy.placycore.core.model.UserModel;
import com.placy.placycore.reviewslearning.data.PredictionData;
import com.placy.placycore.reviewslearning.data.RecommendationEvaluationEntryResultData;
import com.placy.placycore.reviewslearning.strategies.ForcedRecommendationStrategy;
import com.placy.placycore.reviewslearning.strategies.RecommendationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CombinedRecommendationsStrategy implements RecommendationStrategy {

    @Autowired(required = false)
    private List<RecommendationStrategy> recommendationStrategies;

    public List<RecommendationStrategy> getRecommendationStrategies() {
        return recommendationStrategies;
    }

    public void setRecommendationStrategies(List<RecommendationStrategy> recommendationStrategies) {
        this.recommendationStrategies = recommendationStrategies;
    }

    @Override
    public List<PredictionData> getPredictionsForUser(UserModel userModel) {
        List<RecommendationEvaluationEntryResultData> resultDataList = new ArrayList<>();



        return null;
    }

    @Override
    public boolean canPredict(UserModel userModel) {
        return true;
    }

    @Override
    public int getCoeffiecient() {
        return 1;
    }
}
